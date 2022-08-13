<?php


use Restserver\Libraries\REST_Controller;

defined('BASEPATH') or exit('No direct script access allowed');
require APPPATH . 'libraries/REST_Controller.php';
require APPPATH . 'libraries/Format.php';


class Mobileapp extends CI_Controller
{
    use REST_Controller {
        REST_Controller::__construct as private __resTraitConstruct;
    }

    public function __construct()
    {
        parent::__construct();
        $this->__resTraitConstruct();
        $this->load->helper(['jwt']);
        $this->load->model('Mesin_model','mesin');
        $this->load->model('Admin_model','admin');
        $this->load->model('Teknisi_model','teknisi');
        $this->load->model('Notifikasi_model','notif');
    }
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
/* TeknisiApp  */
    public function loginteknisi_post()
    {
        
        $username = $this->post('username');
        $password = $this->post('password');

        $query = $this->db->get_where('teknisi',array('username'=>$username));
        if($query->num_rows() > 0)
        {
            $data_user = $query->row();
            if (password_verify($password, $data_user->password)) {
                $token = JWT::generateToken(['username' => $username]);
                $response = ['code' => 200, 'message' => 'Successful', 'data' => ['token' => $token, 'nama' => $data_user->nama,'id' => $data_user->id, 'alamat' => $data_user->alamat, 'keahlian' => $data_user->keahlian, 'no_telp'=> $data_user->no_telp, 'foto_workshop' => $data_user->foto_workshop, 'foto_diri'=> $data_user->foto_diri, 'username' => $data_user->username]];
                $this->db->insert('log_akun',['tgl_login'=>date('Y-m-d'),'id_akun'=>$data_user->id,'ip_address' => $this->get_client_ip()]);

                $this->response($response, 200,);
            }else{
                $this->response(['code' => 404, 'message' => 'Invalid username or password!'], 200);
            }
        }else{
            $this->response(['code' => 404, 'message' => 'Invalid username or password!'], 200);
        }
    }
    private function generatediagid()
    {
        $kodedata = 'DG-';
        $getlastid = $this->mesin->getlastdgid();
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
    public function teknisiupdatemesin_post()
    {   
        $id_mesin = $this->post('id');
        $id_teknisi = $this->post('id_teknisi');
        $id_diagnosa = $this->post('id_diagnosa');
        
        $est = $this->post('estimasi');

        $status = $this->post('status');



        switch ($status) {
            case "1":
                $data_mesin = array(
                    'status' => $this->post('status')
                );
    
                $data_diagnosa = array(
                    'id' => $this->generatediagid(),
                    'id_mesin' => $id_mesin,
                    'id_teknisi' => $id_teknisi,
                    'tgl_mulai' =>     date("Y-m-d"),
                );
                //disini dia nge insert data diagnosa baru
                if($this->mesin->teknisi_newdiagnosa($id_mesin,$data_mesin,$data_diagnosa)){
                    $response = ['code' => 200, 'message' => 'Berhasil update mesin'];
                    $this->response($response, 200);
                }
                break;
            case "2":
                $data_mesin = array(
                    'status' => $this->post('status')
                );
    
                $data_diagnosa = array(
                    'diagnosa' => $this->post('diagnosa'),
                    'biaya' => $this->post('biaya'),
                    'est_selesai' => $this->post('estimasi'),
                    'tgl_pengajuan' =>     date("Y-m-d"),
                );
                //disini gia ngupdate data diagnosa
                if($this->mesin->teknisi_update_diagnosa($id_mesin,$id_diagnosa,$data_diagnosa,$data_mesin)){
                    $response = ['code' => 200, 'message' => 'Berhasil update mesin'];
                    $this->response($response, 200);
                }
                break;
            case "4":
                $data_mesin = array(
                    'status' => $this->post('status'),
                    'tgl_selesai' =>     date("Y-m-d")
                );
    
                $data_diagnosa = array(
                    'diagnosa' => $this->post('diagnosa'),
                    
                );

                if($this->mesin->teknisi_update_diagnosa($id_mesin,$id_diagnosa,$data_diagnosa,$data_mesin)){
                    $response = ['code' => 200, 'message' => 'Berhasil update mesin'];
                    $this->response($response, 200);
                }
                break;
        }
       
		
        
	
    }

    public function geturlfototeknisi_get()
    {
        // $this->verify_request();
        $id = $this->get('id');
       
        if($this->teknisi->get_fototeknisi($id)){
            $this->response(['code' => 200,'data' => $this->teknisi->get_fototeknisi($id), 200]);
        }

    }
    public function teknisiupdatefoto_post()
    {
       $fotountuk = $this->post('fotountuk');
       $id_teknisi = $this->post('id_teknisi');
       $target_dir = "uploads/";

       
       if($fotountuk == '"profil"'){
        $target_file = $target_dir . uniqid() . '.'.pathinfo($_FILES['file']['name'], PATHINFO_EXTENSION);
        $dataimage = array(
            'foto_diri' => $target_file
        );
                
        //saving the uploaded file to the uploads directory in our target file
        if (move_uploaded_file($_FILES["file"]["tmp_name"], $target_file)) {
            
            // saving the file information to our database
        
            if($this->teknisi->update_teknisi($id_teknisi,$dataimage)){
                $response = ['code' => 200, 'message' => 'Berhasil upload foto'];
                $this->response($response, 200);
            }
      
        }
         }else if($fotountuk === '"workshop"'){
        $target_file = $target_dir . uniqid() . '.'.pathinfo($_FILES['file']['name'], PATHINFO_EXTENSION);
        $dataimage = array(
            'foto_workshop' => $target_file
        );
                
        //saving the uploaded file to the uploads directory in our target file
            if (move_uploaded_file($_FILES["file"]["tmp_name"], $target_file)) {
                
                //saving the file information to our database
            
                if($this->teknisi->update_teknisi($id_teknisi,$dataimage)){
                    $response = ['code' => 200, 'message' => 'Berhasil upload foto'];
                    $this->response($response, 200);
                }
        
            }
        }


    }
    public function teknisigantipw_post()
    {
        // $this->verify_request();
        $id = $this->post('id');
        $pwlama = $this->post('pwlama');
        $pwbaru = $this->post('pwbaru');
        $data_teknisi = array(
			"password" => password_hash($pwbaru ,PASSWORD_DEFAULT),
		);
        $query = $this->db->get_where('teknisi',array('id'=>$id));
        if($query->num_rows() > 0)
        {
            $data_user = $query->row();
            if (password_verify($pwlama, $data_user->password)) {
                 $this->teknisi->update_teknisi($id,$data_teknisi);
                $this->response(['code' => 200, 'message' => 'berhasil'], 200);

            }else{
                $this->response(['code' => 404, 'message' => 'Invalid username or password!'], 200);
            }
        }else{
            $this->response(['code' => 404, 'message' => 'Invalid username or password!'], 200);
        }
       

    }

    public function detailmesindiagnosa_get()
    {
        // $this->verify_request();
        $id = $this->get('id');
        $data = $this->mesin->get_detailmesin_diagnosa($id);
       

        $this->response(['code' => 200, 'message' => 'Successful', 'data' => $data], 200);
    }
    public function detailmesin_get()
    {
        // $this->verify_request();
        $id = $this->get('id');
        $status = $this->get('status');
        if($status < '2'){
            $data = $this->mesin->get_detail_mesin($id);
        }else{
            $data = $this->mesin->get_detailmesin_diagnosa($id);
        }
       

        $this->response(['code' => 200, 'message' => 'Successful', 'data' => $data], 200);
    }
/* -- end of TeknisiApp */

/* AdminApp  */
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

/* -- end of AdminApp */


/* Shared  */
    public function ceknotif_get()
    {
        $this->verify_request();
        $id_user = $this->get('id');
        $status = $this->get('status');
        if($status == "99"){
        $ceknotifteknisi = $this->notif->get_notifikasiteknisiproses($id_user);
    
        }else if($status == "98"){
            $ceknotifteknisi = $this->notif->get_notifikasiteknisibackground($id_user);
        
        }else{
            $ceknotifteknisi = $this->notif->get_notifikasiteknisi($id_user,$status);
        }
        $cekreqfoto = $this->notif->get_allnotifikasirequestfoto();

        if($id_user !== null || $status == "2"){
            if($ceknotifteknisi){
                $this->response(['code' => 200, 'message' => 'Successful', 'data' => $ceknotifteknisi], 200);
            }
        
        }else{
            if($cekreqfoto){
                $this->response(['code' => 200, 'message' => 'Successful', 'data' => $cekreqfoto], 200);
            }
        }
    }

    public function uploadfotomesin_post()
    {
        $this->verify_request();
        $beforaf = $this->post('beforaf');
        $idmesin = $this->post('idmesin');
        $target_dir = "uploads/";

        // if($beforaf=='"before"'){
        //     $beforaf = "betuls";
        // }

        // $response = ['code' => 200, 'message' => $beforaf ];
        //             $this->response($response, 200);
        if($beforaf == '"before"'){
            $target_file = $target_dir . uniqid() . '.'.pathinfo($_FILES['file']['name'], PATHINFO_EXTENSION);
            $dataimage = array(
                'foto_before' => $target_file
            );
                    
            //saving the uploaded file to the uploads directory in our target file
            if (move_uploaded_file($_FILES["file"]["tmp_name"], $target_file)) {
                
                // saving the file information to our database
            
                if($this->mesin->updateimage($idmesin,$dataimage)){
                    $response = ['code' => 200, 'message' => 'Berhasil upload foto'];
                    $this->response($response, 200);
                }
          
            }
        }else if($beforaf === '"after"'){
            $target_file = $target_dir . uniqid() . '.'.pathinfo($_FILES['file']['name'], PATHINFO_EXTENSION);
            $dataimage = array(
                'foto_after' => $target_file
            );
                    
            //saving the uploaded file to the uploads directory in our target file
            if (move_uploaded_file($_FILES["file"]["tmp_name"], $target_file)) {
                
                //saving the file information to our database
            
                if($this->mesin->updateimage($idmesin,$dataimage)){
                    $response = ['code' => 200, 'message' => 'Berhasil upload foto'];
                    $this->response($response, 200);
                }
          
            }
        }
    }

/* -- end of Shared */

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


}