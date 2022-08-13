<?php


use Restserver\Libraries\REST_Controller;

defined('BASEPATH') or exit('No direct script access allowed');
require APPPATH . 'libraries/REST_Controller.php';
require APPPATH . 'libraries/Format.php';


class Webapp extends CI_Controller
{
    use REST_Controller {
        REST_Controller::__construct as private __resTraitConstruct;
    }

    public function __construct()
    {
        parent::__construct();
        $this->__resTraitConstruct();
        $this->load->helper(['jwt']);
        $this->load->dbutil();
        $this->load->helper('file');
        $this->load->model('Mesin_model','mesin');
        $this->load->model('Admin_model','admin');
        $this->load->model('Teknisi_model','teknisi');
        $this->load->model('Pelanggan_model','pelanggan');
        $this->load->model('Notifikasi_model','notif');
    }

/* Auth */
    private function verify_request()
    {
        $headers = $this->input->request_headers();
        if (array_key_exists('Authorization', $headers)) {
            $token = $headers['Authorization'];
            try {
                $data = JWT::validateToken($token);
                if ($data === false) {
                    $response = ['code' => 401, 'message' => 'Unauthorized Access! A'];
                    $this->response($response, 200);
                    exit();
                } else {
                    return $data;
                }
            } catch (Exception $e) {
                $response = ['code' => 401, 'message' => 'Unauthorized Access! B'];
                $this->response($response, 200);
            }
        } else {
            $response = ['code' => 401, 'message' => 'Authorization Not Found'];
            $this->response($response, 200);
        }
    }

    public function logincheck_post()
    {

        $this->verify_request();
        $this->response(['code' => 200, 'message' => 'OK!'], 200);
    }
    
    public function login_post()
    {
        
        $username = $this->post('username');
        $password = $this->post('password');

        $query = $this->db->get_where('administrator',array('username'=>$username));
        if($query->num_rows() > 0)
        {
            $data_user = $query->row();
            if (password_verify($password, $data_user->password)) {
                $token = JWT::generateToken(['username' => $username]);
                $response = ['code' => 200, 'message' => 'Successful', 'data' => ['token' => $token, 'nama' => $data_user->nama, 'id' => $data_user->id,'jabatan' => $data_user->jabatan, 'username' => $data_user->username] ];
                $this->db->insert('log_akun',['tgl_login'=>date('Y-m-d'),'id_akun'=>$data_user->id,'ip_address' => $this->get_client_ip()]);

                $this->response($response, 200,);
            }else{
                $this->response(['code' => 404, 'message' => 'Invalid username or password!'], 200);
            }
        }else{
            $this->response(['code' => 404, 'message' => 'Invalid username or password!'], 200);
        }
    }
/* -- end of Auth */

/* Connectivity */
    public function ping_get()
    {
        $this->response(['code' => 444], 200);
    }
   private function get_client_ip() 
    {
        $ipaddress = '';
        if (getenv('HTTP_CLIENT_IP'))
            $ipaddress = getenv('HTTP_CLIENT_IP');
        else if(getenv('HTTP_X_FORWARDED_FOR'))
            $ipaddress = getenv('HTTP_X_FORWARDED_FOR');
        else if(getenv('HTTP_X_FORWARDED'))
            $ipaddress = getenv('HTTP_X_FORWARDED');
        else if(getenv('HTTP_FORWARDED_FOR'))
            $ipaddress = getenv('HTTP_FORWARDED_FOR');
        else if(getenv('HTTP_FORWARDED'))
           $ipaddress = getenv('HTTP_FORWARDED');
        else if(getenv('REMOTE_ADDR'))
            $ipaddress = getenv('REMOTE_ADDR');
        else
            $ipaddress = 'UNKNOWN';
        return $ipaddress;
    }
/* -- end of Connectivity */

/* dashboard page */
    public function dashboardstatistik_get()
    {
        $this->verify_request();
        $blok_mesin = array(
            'bulan_ini' => $this->mesin->jumlahmesinbulanini(),
            'sekarang' => $this->mesin->jumlahmesinsekarang(),
            'kemarin' =>$this->mesin->jumlahmesinkemarin(),
        );
        $blok_performa = array(
            'bulan_ini' => $this->mesin->jumlahmesinbulanini_selesai(),
            'bulan_kemarin' => $this->mesin->jumlahmesinbulankemarin_selesai(),
            'performa_bulan_ini' => $this->mesin->performabulanini(),
            'performa_bulan_kemarin' => $this->mesin->performabulankemarin(),
        );
        
        $blok_pelanggan = $this->pelanggan->get_pelanggan_terkini();

        $blok_teknisi = $this->teknisi->get_teknisi_terkini();
        

        $response = ['code' => 200, 'blok_mesin'=> $blok_mesin, 'blok_performa'=> $blok_performa, 'blok_pelanggan' => $blok_pelanggan, 'blok_teknisi' => $blok_teknisi];
        $this->response($response, 200);
    
        
    }

    public function getdashboardnotif_get()
    {
        $this->verify_request();
        $id_mesin = $this->get('id_mesin');
        if($id_mesin != null){
            $notifdashboard = $this->notif->get_detailnotifdashboard($id_mesin);

        }else{

            $notifdashboard = $this->notif->get_notifdashboard();
        }

        $this->response(['code' => 200, 'message' => 'ACC Diagnosa Mesin','data' => $notifdashboard, 200]);

    }

    public function adminaccpengajuan_post()
    {
         $this->verify_request();
        $id_mesin = $this->post('id_mesin');
        $id_diagnosa = $this->post('id_diagnosa');
        $id_teknisi = $this->post('id_teknisi');
        $acc = $this->admin->admin_accpengajuan($id_mesin,$id_diagnosa);
        $teknisi = $this->teknisi->teknisi_tambah_diproses($id_teknisi);
        if($acc && $teknisi){
             $this->response(['code' => 200, 'message' => 'Successful', 200]);
        }

    }
/* --end of dashboard page */

/* tambah mesin page */
    public function teknisisearch_get()
    {
        $this->verify_request();
        $term = $this->get('search');
        if($teknisi = $this->teknisi->teknisi_cari($term)){
            $this->response(['code' => 200, 'message' => 'Successful', 'data' => $teknisi], 200);
        }
    }

    private function generatemesinid()
    {
        //id pelanggannya ngambil yg dibawah yg buat hlm pelanggan
        $kodedata = 'MS-';
        $getlastid = $this->mesin->getlastid();
        $urutan = 0;
        //cektanggal
        $tanggallastcreatedteknisi = substr($getlastid->id,3,6);
        $tigadigitterakhir = substr($getlastid->id,-3);
        $tglskrg = date("dmy");
        if($tanggallastcreatedteknisi == $tglskrg){
            $urutan = $tigadigitterakhir;
            
        }
        $urutansekarang = $kodedata.$tglskrg.sprintf("%03s", ++$urutan);
        return $urutansekarang;
        //cocokin dengan date today format = dd-mm-yy
        //kalo cocok berarti tambahin 3 digit terakhir dengan sprintf("%03s", lastthreedigits++)
        //kalo ga cocok berarti dari 0 lagi 
    }

    public function tambahmesin_post()
	{
        $this->verify_request();
        $dataPmk = $this->post('dataPmk');
        $dataMsn = $this->post('dataMsn');
        $dataCount = count($dataPmk)+ count($dataMsn);
        $i = 0;
        foreach($dataPmk as $d){
            if($d['dafpel'] == null){
                 $datafinal = array(
                'id' => $d['id'],
                'nama' => $d['pemilik'],
                'alamat' => $d['alamat'],
                'no_telp' => $d['telp'],
                'tgl_dibuat' =>     date("Y-m-d")

                //tinggal bikin data mes
            );

            $this->pelanggan->tambah_pelanggan($datafinal);
            }
           

            if(++$i === $dataCount){
                $response = ['code' => 200, 'message' => 'berhasil di input' ];
                $this->response($response, 200);
            }
            
        }
        foreach($dataMsn as $i=>$d){

            $idMesin = $this->generatemesinid();
            $datafinal = array(
                'id' => $idMesin,
                'jenis_mesin' => $d['jenis'],
                'merek' => $d['merk'],
                'keterangan' => $d['keterangan'],
                'tgl_masuk' =>     date("Y-m-d"),
                'status' => '0',
                //tinggal bikin data mes
            );
            $this->mesin->tambah_mesin($datafinal);

            $datakpmk = array(
                'id_mesin' => $idMesin,
                'id_pelanggan' => $d['id'],
                'id_teknisi' => $d['teknisi']
            );

            $this->mesin->tambah_mesin_kepemilikan($datakpmk);


            if(++$i === $dataCount){
                $response = ['code' => 200, 'message' => 'berhasil di input' ];
                $this->response($response, 200);
            }
          
        }
		
           
            
	}
/* -- end of tambah mesin page */

/* daftar mesin page */
    public function daftarmesin_get()
    {
        $this->verify_request();
        $id = $this->get('id');
        
        if ($id !== null) {
            $mesin = $this->mesin->get_mesin($id);
        } else {
            $mesin = $this->mesin->get_all_mesin();
        }
        
        $this->response(['code' => 200, 'message' => 'Successful', 'data' => $mesin], 200);
    }

    public function detailmesin_get()
    {
        $this->verify_request();
        $id = $this->get('id');
        $data = $this->mesin->get_detail_mesin($id);
        $datadiag = $this->mesin->get_detailmesin_diagnosa($id);
       

        $this->response(['code' => 200, 'message' => 'Successful', 'data' => $data,'datadiag' => $datadiag], 200);
    }
    
    public function updatemesin_post()
    {
        $this->verify_request();
        $id = $this->post('id');

        $data_teknisi= "";

        if($this->post('teknisi') != null){
            	$data_mesin = array(
			'jenis_mesin' => $this->post('jenis'),
			'merek' => $this->post('merek'),
			'keterangan' => $this->post('keterangan'));
            $data_teknisi = array(
			
			'id_teknisi' => $this->post('teknisi'),
		);
        }else{
            $data_mesin = array(
                'jenis_mesin' => $this->post('jenis'),
                'merek' => $this->post('merek'),
                'keterangan' => $this->post('keterangan')
            );
        }
	
        
		if($this->mesin->update_mesin($id,$data_mesin,$data_teknisi)){
            $response = ['code' => 200, 'message' => 'Berhasil update mesin'];
            $this->response($response, 200);
        }
    }
   
    public function hapusmesin_post()
    {
        $this->verify_request();
        $id = $this->post('id');
        if($this->mesin->hapus_mesin($id)){
            $response = ['code' => 200, 'message' => 'Berhasil hapus mesin'];
            $this->response($response, 200);
        }
    }
/* -- end of daftar mesin page */

/* daftar teknisi page */
    private function generateteknisiid()
    {
        $kodedata = 'TK-';
        $getlastid = $this->teknisi->getlastid();
        $urutan = 0;
        //cektanggal
        $tanggallastcreatedteknisi = substr($getlastid->id,3,6);
        $tigadigitterakhir = substr($getlastid->id,-3);
        $tglskrg = date("dmy");
        if($tanggallastcreatedteknisi == $tglskrg){
            $urutan = $tigadigitterakhir;
            
        }
        $urutansekarang = $kodedata.$tglskrg.sprintf("%03s", ++$urutan);

        return $urutansekarang;
        //cocokin dengan date today format = dd-mm-yy
        //kalo cocok berarti tambahin 3 digit terakhir dengan sprintf("%03s", lastthreedigits++)
        //kalo ga cocok berarti dari 0 lagi 
    }

    public function daftarteknisi_get()
    {
        $this->verify_request();
        $id = $this->get('id');
        if ($id !== null) {
            $teknisi = $this->teknisi->get_teknisi($id);
        } else {
            $teknisi = $this->teknisi->get_all_teknisi();
        }
        $this->response(['code' => 200, 'message' => 'Successful', 'data' => $teknisi], 200);
    }

    public function teknisimesindiproses_get()
    {
        $this->verify_request();
        $id = $this->get('id_teknisi');
        $data = $this->mesin->mesinbyteknisi($id);
        if($data){
            $this->response(['code' => 200, 'message' => 'Successful','data'=>$data, 200]);
        }else{
            $this->response(['code' => 200, 'message' => 'Kosong','data'=>$data, 200]);
        }
    }

    public function registerteknisi_post()
    {
        $this->verify_request();   
        $username = $this->post('username');
        $check = $this->teknisi->cek_username($username);

        if($check == 0){
           
            $data_teknisi = array(
                'id'=> $this->generateteknisiid(),
                'username'=>$this->post('username'),
                'nama'=>$this->post('nama'),
                'alamat'=>$this->post('alamat'),
                'keahlian'=>$this->post('keahlian'),
                'password'=>password_hash("welcome2022",PASSWORD_DEFAULT),
                'no_telp'=>$this->post('telepon')
            );
    
            $data_log_akun = array(
                'id_akun'=> $this->generateteknisiid(),
                'tgl_dibuat' => date("Y-m-d"),
            );
    
            if($this->teknisi->tambah_teknisi($data_teknisi) && $this->teknisi->isi_log_akun($data_log_akun)){
                $response = ['code' => 200, 'message' => 'Berhasil registrasi teknisi'];
                $this->response($response, 200);
            }

        }else{
                $response = ['code' => 777, 'message' => 'username sudah digunakan'];
                $this->response($response, 200);
        }	
        
    }

    public function teknisiedit_get()
    {
        $this->verify_request();
        $id = $this->get('id');
        $teknisi = $this->teknisi->get_teknisi_edit($id);
        $this->response(['code' => 200, 'message' => 'Successful', 'data' => $teknisi], 200);
    }

    public function teknisiedit_post()
    {
        $this->verify_request();
        $id = $this->post('id');
        
        if($this->post('password') != ""){
        $data_teknisi = array(
			'nama'=> $this->post('nama'),
			'username'=> $this->post('username'),
			'alamat'=> $this->post('alamat'),
			'no_telp'=> $this->post('no_telp'),
			'keahlian'=> $this->post('keahlian'),
			'password'=> password_hash($this->post('password'),PASSWORD_DEFAULT)
		);
       
        }else{
            $data_teknisi = array(
                'nama'=> $this->post('nama'),
                'username'=> $this->post('username'),
                'alamat'=> $this->post('alamat'),
                'no_telp'=> $this->post('no_telp'),
                'keahlian'=> $this->post('keahlian')
            );
        }

        $data_log_akun = array(
            'id_akun'=> $id,
            'tgl_update' =>  date("Y-m-d"),
        );
        
        $this->teknisi->update_teknisi($id,$data_teknisi);
        $this->teknisi->isi_log_akun($data_log_akun);
        $this->response(['code' => 200, 'message' => 'Successful', 200]);
    }

    public function teknisihapus_post()
    {
        $this->verify_request();
        $id = $this->post('id');
       
        if($this->teknisi->hapus_teknisi($id)){
            $this->response(['code' => 200, 'message' => 'Successful', 200]);
        }

    }
    
/* -- end of daftar teknisi page */

/* daftar pelanggan page */

    public function generatepelangganid_get()
    {
        $kodedata = 'PG-';
        $getlastid = $this->pelanggan->getlastid();
        $urutan = 0;
        //cektanggal
        $tanggallastcreatedteknisi = substr($getlastid->id,3,6);
        $tigadigitterakhir = substr($getlastid->id,-3);
        $tglskrg = date("dmy");
        if($tanggallastcreatedteknisi == $tglskrg){
            $urutan = $tigadigitterakhir;
            
        }
        $urutansekarang = $kodedata.$tglskrg.sprintf("%03s", ++$urutan);
        $this->response(['code' => 200, 'message' => 'berhasil generate','id' =>$urutansekarang, 200]);
        //cocokin dengan date today format = dd-mm-yy
        //kalo cocok berarti tambahin 3 digit terakhir dengan sprintf("%03s", lastthreedigits++)
        //kalo ga cocok berarti dari 0 lagi 
    }
    public function mesinmilikpelanggan_get(){
        $this->verify_request();
        $id = $this->get('id_pelanggan');
        $data = $this->mesin->mesinbypelanggan($id);
        if($data){
            $this->response(['code' => 200, 'message' => 'Successful','data'=>$data, 200]);
        }else{
            $this->response(['code' => 200, 'message' => 'Kosong','data'=>$data, 200]);
        }
    }

    public function daftarpelanggan_get()
    {
        $this->verify_request();
        $id = $this->get('id');
        if ($id !== null) {
            $pelanggan = $this->pelanggan->get_pelanggan($id);
        } else {
            $pelanggan = $this->pelanggan->get_all_pelanggan();
        }
        $this->response(['code' => 200, 'message' => 'Successful', 'data' => $pelanggan], 200);
    }

    public function pelangganedit_post()
    {
        $this->verify_request();
        $id = $this->post('id');
        $data_pelanggan = array(
            'id' => $this->post('id'),
            'nama' => $this->post('nama'),
            'alamat' => $this->post('alamat'),
            'no_telp' => $this->post('no_telp'),
        );

        $data_log_akun = array(
            'id_akun'=> $id,
            'tgl_update' =>  date("Y-m-d"),
        );
        $this->pelanggan->update_pelanggan($id,$data_pelanggan);
        $this->teknisi->isi_log_akun($data_log_akun);
        $this->response(['code' => 200, 'message' => 'Successful', 200]);
    }

    public function pelangganhapus_post()
    {
        $this->verify_request();
        $id = $this->post('id');
       
        if($this->pelanggan->hapus_pelanggan($id)){
            $this->response(['code' => 200, 'message' => 'Successful', 200]);
        }
    }
/* -- end of daftar pelanggan page */

/* settings page */
    public function getloglogin_get()
    {
        
        $this->verify_request();
        
        $query = $this->db->where('tgl_login !=',null)->get('log_akun')->result();

        $this->response(['code' => 200, 'message' => 'ok!','data' => $query], 200);
    
    }

    public function backupdb_get()
    {
        $this->verify_request();
        $backup = $this->dbutil->backup();
        $filename = date('Y-m-d').uniqid();

        // Load the file helper and write the file to your server
        write_file('backup/backupdb'.$filename.'.gz', $backup);
        $now = $this->db->insert('log_backup', ['tanggal' => date('Y-m-d'),'nama_file' => $filename.'.gz']);

        if($now) {
            $response = ['code' => 200, 'message' => 'Berhasil Data'];
            $this->response($response, 200);
        }
        
    
    }

    public function badckupdblist_get()
    {
        $this->verify_request();
        
        $query = $this->db->get('log_backup')->result();

        $this->response(['code' => 200, 'message' => 'ok!','data' => $query], 200);
    }

    public function truncatedb_get()
    {
        $this->verify_request();
        $this->db->truncate('log_akun');
        $this->db->truncate('mesin_kepemilikan');
        $this->db->truncate('mesin_diagnosa');
        $this->db->empty_table('mesin');
        $this->db->empty_table('pelanggan');
        $this->db->empty_table('teknisi');
    

        $this->response(['code' => 200, 'message' => 'ok!']);
    }

    private function generateadminid()
    {
        $kodedata = 'AD-';
        $getlastid = $this->admin->getlastid();
        $urutan = 0;

        //cektanggal
        $tanggallastcreatedteknisi = substr($getlastid->id,3,6);
        $tigadigitterakhir = substr($getlastid->id,-3);
        $tglskrg = date("dmy");

        if($tanggallastcreatedteknisi == $tglskrg){
            $urutan = $tigadigitterakhir;
        }

        $urutansekarang = $kodedata.$tglskrg.sprintf("%03s", ++$urutan);
        return $urutansekarang;
        //cocokin dengan date today format = dd-mm-yy
        //kalo cocok berarti tambahin 3 digit terakhir dengan sprintf("%03s", lastthreedigits++)
        //kalo ga cocok berarti dari 0 lagi 
    }

    function registeradmin_post()
	{
        $this->verify_request();
        $username = $this->post('username');
        $check = $this->admin->cek_username($username);

        if($check == 0){
            $data_admin = array(
                'id' => $this->generateadminid(),
                'username'=>$username,
                'password'=>password_hash($this->post('password'),PASSWORD_DEFAULT),
                'nama'=>$this->post('nama'),
                'jabatan'=>'2'
            );

            $data_log_akun = array(
                'id_akun'=> $this->generateadminid(),
                'tgl_dibuat' =>     date("Y-m-d"),
            );

            if($this->admin->tambah_admin($data_admin)  && $this->teknisi->isi_log_akun($data_log_akun)){
                $response = ['code' => 200, 'message' => 'Berhasil registrasi admin'];
                $this->response($response, 200);
            }

        }else{
                $response = ['code' => 777, 'message' => 'username sudah digunakan'];
                $this->response($response, 200);
        }	

	}

    function editadmin_post()
	{
        $this->verify_request();
        $id = $this->post('id');
		$data_admin = array(
			'username'=>$this->post('username'),
			'password'=>password_hash($this->post('password'),PASSWORD_DEFAULT),
			'nama'=>$this->post('nama'),
            'jabatan'=>'2'
		);
        $data_log_akun = array(
            'id_akun'=> $id,
            'tgl_update' =>     date("Y-m-d"),
        );

		if($this->admin->update_admin($id,$data_admin) && $this->teknisi->isi_log_akun($data_log_akun)){
            $response = ['code' => 200, 'message' => 'Berhasil update admin'];
            $this->response($response, 200);
        }
	}

    public function adminhapus_post()
    {
        $this->verify_request();
        $id = $this->post('id');
       
        if($this->admin->hapus_admin($id)){
            $this->response(['code' => 200, 'message' => 'Successful', 200]);
        }
    }

    function updatemanpsw_post()
    {
        $this->verify_request();
        $id = $this->post('id');
        if($this->post('password') != null){
            $data_manager = array(
			'username'=>$this->post('username'),
			'password'=>password_hash($this->post('password'),PASSWORD_DEFAULT),
			'nama'=>$this->post('nama')
		);
        }else{  
            $data_manager = array(
			'username'=>$this->post('username'),
			'nama'=>$this->post('nama')
		);
            
        }
        $data_log_akun = array(
            'id_akun'=> $id,
            'tgl_update' =>     date("Y-m-d"),
        );
		if($this->admin->update_manager_psw($id,$data_manager) && $this->teknisi->isi_log_akun($data_log_akun)){
            $response = ['code' => 200, 'message' => 'Berhasil update manager'];
            $this->response($response, 200);
        }
    }

    public function daftaradmin_get()
    {
        $this->verify_request();
        $id = $this->get('id');
        if ($id !== null) {
            $admin = $this->admin->get_admin($id);
        } else {
            $admin = $this->admin->get_all_admin();
        }
        $this->response(['code' => 200, 'message' => 'Successful', 'data' => $admin], 200);
    }
    
    public function managername_get()
    {
        $this->verify_request();
        $id = $this->get('id');
        if ($id !== null) {
            $admin = $this->admin->get_admin($id);
        } else {
            $admin = $this->admin->get_all_manager();
        }
        $this->response(['code' => 200, 'message' => 'Successful', 'data' => $admin], 200);
    }
/* -- end of settings page */





}