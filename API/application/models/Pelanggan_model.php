<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class Pelanggan_model extends CI_Model {

    public function get_pelanggan($id)
    {
        $cekpelanggan = $this->db->select('id_pelanggan')->from('mesin_kepemilikan')->where('id_pelanggan',$id)->count_all_results();


        if($cekpelanggan > 0){
            $this->db->select('pelanggan.nama,pelanggan.alamat,pelanggan.id,pelanggan.no_telp,count(mesin_kepemilikan.id_pelanggan) as jml_diproses,pelanggan.tgl_dibuat');
            $this->db->join('mesin_kepemilikan','mesin_kepemilikan.id_pelanggan = pelanggan.id');
            $this->db->from('pelanggan');
            $this->db->where('pelanggan.id', $id);
        }else{
            $this->db->select('pelanggan.nama,pelanggan.alamat,pelanggan.id,pelanggan.no_telp,pelanggan.tgl_dibuat');
            $this->db->from('pelanggan');
            $this->db->where('pelanggan.id', $id);
        }

        
        return $this->db->get()->row();
    }

    public function getlastid(){
        $this->db->select_max('id');
        $this->db->from('pelanggan');
        return $this->db->get()->row();
    }

    public function get_nama_pelanggan($id)
    {
        $this->db->select('nama','alamat');
        $this->db->where('id', $id);
        return $this->db->get('pelanggan')->row();
    }

    public function get_all_pelanggan()
    {
        $this->db->select('pelanggan.nama,pelanggan.alamat,pelanggan.id,pelanggan.no_telp,count(mesin_kepemilikan.id_pelanggan) as jml_diproses');
        $this->db->join('mesin_kepemilikan','mesin_kepemilikan.id_pelanggan = pelanggan.id','left');
        $this->db->from('pelanggan');
        $this->db->group_by('pelanggan.id');
        return $this->db->get()->result();
    }
    
    public function get_pelanggan_terkini()
    {
        $this->db->select('pelanggan.id,pelanggan.nama,pelanggan.no_telp,pelanggan.alamat');
        
        $this->db->join('pelanggan','pelanggan.id = mesin_kepemilikan.id_pelanggan');
        $this->db->distinct();
        return $this->db->get('mesin_kepemilikan',3)->result();
    }

    public function tambah_pelanggan($data)
    {
        return $this->db->insert('pelanggan',$data);
    }
    public function update_pelanggan($id,$new)
    {
        $this->db->where('id',$id);
        return $this->db->update('pelanggan',$new);
    }

    public function isi_log_akun($data)
    {
        return $this->db->insert('log_akun',$data);
    }

    public function hapus_pelanggan($id)
    {   
        
        $this->db->where('id_pelanggan',$id)->delete('mesin_kepemilikan');
        $this->db->where('id',$id);
        return $this->db->delete('pelanggan');
    }


}

/* End of file Dokter_model.php */