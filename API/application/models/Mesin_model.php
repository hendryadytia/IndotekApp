<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class Mesin_model extends CI_Model {

    public function get_detail_mesin($id)
    {   
        $cekteknisi = $this->db->select('id_teknisi')
                ->where('id_mesin', $id)
                 ->get('mesin_kepemilikan')->row();

        
        if($cekteknisi->id_teknisi != null){
            $this->db->select('mesin.jenis_mesin,mesin.merek,mesin.keterangan,pelanggan.nama as nama_pelanggan,pelanggan.id as id_pelanggan,pelanggan.alamat,pelanggan.no_telp as no_telp_pelanggan,teknisi.nama as nama_teknisi,mesin.tgl_selesai,mesin_status.nama_status,mesin.status,mesin.foto_before,mesin.foto_after');
            $this->db->from('mesin_kepemilikan');
            $this->db->join('mesin','mesin.id = mesin_kepemilikan.id_mesin');
            $this->db->join('mesin_status','mesin_status.id = mesin.status');
            // $this->db->join('mesin_diagnosa','mesin_diagnosa.id_mesin = mesin_kepemilikan.id_mesin');
            $this->db->join('pelanggan','pelanggan.id = mesin_kepemilikan.id_pelanggan');
            $this->db->join('teknisi','teknisi.id = mesin_kepemilikan.id_teknisi');
            $this->db->where('mesin_kepemilikan.id_mesin', $id);    
        }else{
            $this->db->select('mesin.jenis_mesin,mesin.merek,mesin.keterangan,pelanggan.nama as nama_pelanggan,pelanggan.id as id_pelanggan,pelanggan.alamat,pelanggan.no_telp as no_telp_pelanggan,mesin.tgl_selesai,mesin_status.nama_status,mesin.status,mesin.foto_before,mesin.foto_after');
            $this->db->from('mesin_kepemilikan');
            $this->db->join('mesin','mesin.id = mesin_kepemilikan.id_mesin');
            $this->db->join('mesin_status','mesin_status.id = mesin.status');
            // $this->db->join('mesin_diagnosa','mesin_diagnosa.id_mesin = mesin_kepemilikan.id_mesin');
            $this->db->join('pelanggan','pelanggan.id = mesin_kepemilikan.id_pelanggan');
            $this->db->where('mesin_kepemilikan.id_mesin', $id);    
        }
        
       
        return $this->db->get()->row();
    }

    public function get_detailmesin_diagnosa($id){

        $idteknisi = $this->db->select('id_teknisi')->from('mesin_kepemilikan')->where('mesin_kepemilikan.id_mesin',$id)->get()->row()->id_teknisi;
        $iddiag = $this->db->select_max('mesin_diagnosa.id')
        ->from('mesin_kepemilikan')
        ->join('mesin_diagnosa','mesin_diagnosa.id_mesin = mesin_kepemilikan.id_mesin')->where('mesin_kepemilikan.id_mesin', $id)->where('mesin_diagnosa.id_teknisi',$idteknisi)->get()->row()->id;

        $this->db->select('mesin_diagnosa.id as id_diagnosa,mesin.id as id_mesin,mesin_diagnosa.diagnosa, mesin_diagnosa.biaya,mesin_diagnosa.est_selesai,mesin.status,mesin.jenis_mesin,mesin.merek,mesin.keterangan,mesin.foto_before,mesin.foto_after,mesin_diagnosa.tgl_mulai');
        $this->db->from('mesin_diagnosa');
        $this->db->join('mesin','mesin.id = mesin_diagnosa.id_mesin');
        $this->db->where('mesin_diagnosa.id',$iddiag);
        return $this->db->get()->row();

    }

    public function get_mesin($id){
        $this->db->select('mesin.jenis_mesin,mesin.merek,mesin.keterangan');
        $this->db->from('mesin_kepemilikan');
        $this->db->join('mesin','mesin.id = mesin_kepemilikan.id_mesin');
        $this->db->where('mesin_kepemilikan.id_mesin', $id);
        return $this->db->get()->row();
    }

    public function getlastid(){
        $this->db->select_max('id');
        $this->db->from('mesin');
        return $this->db->get()->row();
    }
    public function getlastdgid(){
        $this->db->select_max('id');
        $this->db->from('mesin_diagnosa');
        return $this->db->get()->row();
    }



    public function get_all_mesin()
    {
        $this->db->select('mesin.id,mesin.jenis_mesin,pelanggan.nama as nama_pemilik,mesin.tgl_masuk,mesin_status.nama_status as status,teknisi.nama as teknisi');
        $this->db->from('mesin_kepemilikan');
        $this->db->join('mesin','mesin.id = mesin_kepemilikan.id_mesin');
        $this->db->join('mesin_status','mesin_status.id = mesin.status');
        $this->db->join('pelanggan','pelanggan.id = mesin_kepemilikan.id_pelanggan');
        $this->db->join('teknisi','teknisi.id = mesin_kepemilikan.id_teknisi','left');

        return $this->db->get()->result();
    }

    public function tambah_mesin($data)
    {
        return $this->db->insert('mesin',$data);
    }

    public function tambah_mesin_kepemilikan($data)
    {
        return $this->db->insert('mesin_kepemilikan',$data);
    }

    public function update_mesin($id,$new,$datateknisi)
    {
        if($datateknisi != null){
            $this->db->where('id_mesin',$id);
            $this->db->update('mesin_kepemilikan',$datateknisi);
        }

        $this->db->where('id',$id);
        return $this->db->update('mesin',$new);
    }

    public function teknisi_newdiagnosa($id_mesin,$data_mesin,$data_diagnosa){
        $this->db->insert("mesin_diagnosa",$data_diagnosa);

        $this->db->where('id',$id_mesin);
        return $this->db->update('mesin',$data_mesin);
        
    }

    public function teknisi_update_diagnosa($id_mesin,$id_diagnosa,$data_diagnosa,$data_mesin){
        $this->db->where('id',$id_mesin)->update('mesin',$data_mesin);
        return $this->db->where('id',$id_diagnosa)->update('mesin_diagnosa',$data_diagnosa);
    }

    public function updateimage($id,$new)
    {
        $this->db->where('id',$id);
        return $this->db->update('mesin',$new);
    }

    public function hapus_mesin($id)
    {
        $this->db->where('id',$id);
        return $this->db->delete('mesin');
    }
    
    public function jumlahmesinbulankemarin()
    {
        $thismonth = (int) date('n', strtotime('-1 months'));
        $ifjanuary = (int) date('Y', strtotime('-1 months')); 
        $this->db->from('mesin');
        $this->db->where('MONTH(tgl_masuk)', $thismonth);
        $this->db->where('YEAR(tgl_masuk)', $ifjanuary);
        return $this->db->count_all_results();;
    }
    public function jumlahmesinbulankemarin_selesai()
    {
        $thismonth = (int) date('n', strtotime('-1 months'));
        $ifjanuary = (int) date('Y', strtotime('-1 months')); 
        $this->db->from('mesin');
        $this->db->where('MONTH(tgl_masuk)', $thismonth);
        $this->db->where('YEAR(tgl_masuk)', $ifjanuary);
        $this->db->where('status', "4");

        return $this->db->count_all_results();;
    }
    public function jumlahmesinbulanini()
    {
        $thismonth = (int) date('n', time());
        $ifjanuary = (int) date('Y', time()); 
        $this->db->from('mesin');
        $this->db->where('MONTH(tgl_masuk)', $thismonth);
        $this->db->where('YEAR(tgl_masuk)', $ifjanuary);
        return $this->db->count_all_results();;
    }
    public function jumlahmesinbulanini_selesai()
    {
        $thismonth = (int) date('n', time());
        $ifjanuary = (int) date('Y', time()); 
        $this->db->from('mesin');
        $this->db->where('MONTH(tgl_masuk)', $thismonth);
        $this->db->where('YEAR(tgl_masuk)', $ifjanuary);
        $this->db->where('status', "4");

        return $this->db->count_all_results();;
    }
    public function jumlahmesinsekarang()
    {
        $thismonth = (int) date('n', time());
        $thisday = (int) date('d', time());
        $ifjanuary = (int) date('Y', time()); 
        $this->db->from('mesin');
        $this->db->where('DAY(tgl_masuk)', $thisday);
        $this->db->where('MONTH(tgl_masuk)', $thismonth);
        $this->db->where('YEAR(tgl_masuk)', $ifjanuary);
        return $this->db->count_all_results();;
    }

    public function jumlahmesinkemarin()
    {
        $thismonth = (int) date('n', time());
        $thisday = (int) date('d', strtotime('-1 days'));
        $ifjanuary = (int) date('Y', time()); 
        $this->db->from('mesin');
        $this->db->where('DAY(tgl_masuk)', $thisday);
        $this->db->where('MONTH(tgl_masuk)', $thismonth);
        $this->db->where('YEAR(tgl_masuk)', $ifjanuary);
        return $this->db->count_all_results();;
    }
    public function jumlahmesinmilikpelanggan($id)
    {
      
        $this->db->from('mesin_kepemilikan');
        $this->db->where('id_pelanggan',$id);
        return $this->db->count_all_results();;
    }

    public function performabulanini()
    {   

        $thismonth = (int) date('n', time());
        $ifjanuary = (int) date('Y', time()); 
        $this->db->select_avg('mesin_diagnosa.est_selesai');
        $this->db->from('mesin');
        $this->db->join('mesin_diagnosa','mesin_diagnosa.id_mesin = mesin.id');
        $this->db->where('MONTH(mesin.tgl_masuk)', $thismonth);
        $this->db->where('YEAR(mesin.tgl_masuk)', $ifjanuary);
        $this->db->where('mesin.status', "4");
        $this->db->where('mesin_diagnosa.tgl_acc is NOT NULL', NULL,FALSE);

        return $this->db->get()->row();
    }
    public function performabulankemarin()
    {
        $thismonth = (int) date('n', strtotime('-1 months'));
        $ifjanuary = (int) date('Y', strtotime('-1 months'));
        $this->db->select_avg('mesin_diagnosa.est_selesai');
        $this->db->from('mesin');
        $this->db->join('mesin_diagnosa','mesin_diagnosa.id_mesin = mesin.id');
        $this->db->where('MONTH(mesin.tgl_masuk)', $thismonth);
        $this->db->where('YEAR(mesin.tgl_masuk)', $ifjanuary);
        $this->db->where('mesin.status', "4");
        $this->db->where('mesin_diagnosa.tgl_acc is NOT NULL', NULL,FALSE);
        
        return $this->db->get()->row();
    }

    public function mesinbyteknisi($id)
    {
        $this->db->select('mesin.jenis_mesin,pelanggan.nama,mesin.tgl_selesai,mesin_status.nama_status');
        $this->db->from('mesin_kepemilikan');
        $this->db->join('mesin','mesin.id = mesin_kepemilikan.id_mesin');
        $this->db->join('pelanggan','pelanggan.id = mesin_kepemilikan.id_pelanggan');
        $this->db->join('mesin_status','mesin_status.id = mesin.status');
        $this->db->where('mesin.status !=','0');
        $this->db->where('mesin_kepemilikan.id_teknisi',$id);
        return $this->db->get()->result();
    }
    public function mesinbypelanggan($id)
    {
        $this->db->select('mesin.id,mesin.jenis_mesin, teknisi.nama,mesin.tgl_masuk,mesin_status.nama_status');
        $this->db->from('mesin_kepemilikan');
        $this->db->join('mesin','mesin.id = mesin_kepemilikan.id_mesin');
        $this->db->join('teknisi','teknisi.id = mesin_kepemilikan.id_teknisi','left');
        $this->db->join('mesin_status','mesin_status.id = mesin.status');
        $this->db->where('mesin_kepemilikan.id_pelanggan',$id);
        return $this->db->get()->result();
    }
   


}

/* End of file Dokter_model.php */