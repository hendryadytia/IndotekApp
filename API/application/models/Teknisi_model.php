<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class Teknisi_model extends CI_Model {

    public function get_teknisi($id)
    {
        $this->db->select('teknisi.nama,teknisi.username,teknisi.alamat,teknisi.foto_workshop,teknisi.foto_diri,teknisi.id,teknisi.keahlian,teknisi.no_telp,teknisi.jml_diproses, log_akun.tgl_dibuat');
        $this->db->join('log_akun','log_akun.id_akun = teknisi.id');
        $this->db->from('teknisi');
        $this->db->where('teknisi.id', $id);
        $this->db->where('log_akun.id_akun', $id);
        $this->db->where('log_akun.tgl_dibuat is NOT NULL',NULL,FALSE);
        $this->db->group_by('log_akun.tgl_dibuat');

        return $this->db->get()->row();
    }

    public function getlastid(){
        $this->db->select_max('id');
        $this->db->from('teknisi');
        return $this->db->get()->row();
    }

    public function cek_username($username){
        $this->db->where('username',$username);
        return $this->db->get('teknisi')->num_rows();
    }
    
    
    public function get_fototeknisi($id)
    {   
        $this->db->select('foto_diri,foto_workshop');
        $this->db->where('id', $id);
        return $this->db->get('teknisi')->row();
    }
    
    public function get_teknisi_edit($id)
    {
        $this->db->select('id,
        nama,
        username,
        alamat,
        no_telp,
        keahlian');
        $this->db->where('id', $id);
        return $this->db->get('teknisi')->row();
    }

    public function get_all_teknisi()
    {
        $this->db->select('id,
        nama,
        alamat,
        keahlian,
        jml_diproses');
        return $this->db->get('teknisi')->result();
    }

    public function tambah_teknisi($data)
    {
        return $this->db->insert('teknisi',$data);
    }
    
    public function isi_log_akun($data)
    {
        return $this->db->insert('log_akun',$data);
    }

    public function update_teknisi($id,$data)
    {
        $this->db->where('id', $id);
        return $this->db->update('teknisi',$data);
    }

    public function hapus_teknisi($id)
    {
        $this->db->where('id',$id);
        return $this->db->delete('teknisi');
    }

    public function teknisi_cari($term)
    {
        $response = array();

       
          // Select record
          $this->db->select('id,nama');
          $this->db->where("nama like '%".$term."%' ");
   
          $records = $this->db->get('teknisi')->result();
   
          foreach($records as $row ){
             $response[] = array("value"=>$row->id,"label"=>$row->nama);
          }
   
        
   
        return $response;
    }
    public function get_teknisi_terkini()
    {
        $this->db->select('teknisi.id,teknisi.nama,teknisi.no_telp,teknisi.alamat');
        
        $this->db->join('teknisi','teknisi.id = mesin_kepemilikan.id_teknisi');
        $this->db->distinct();
        return $this->db->get('mesin_kepemilikan',3)->result();
    }
    
    public function teknisi_tambah_diproses($id_teknisi){
        $this->db->where('id', $id_teknisi);
        $this->db->set('jml_diproses', 'jml_diproses+1', FALSE);
        return $this->db->update('teknisi');
    }
    

}

/* End of file Dokter_model.php */