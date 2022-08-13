<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class Admin_model extends CI_Model {

    public function get_admin($id)
    {
        $this->db->where('id', $id);
        return $this->db->get('administrator')->row();
    }

    public function getlastid(){
        $this->db->select_max('id');
        $this->db->from('administrator');
        return $this->db->get()->row();
    }

    public function cek_username($username){
        $this->db->where('username',$username);
        return $this->db->get('administrator')->num_rows();
    }

    public function get_all_admin()
    {   
        $this->db->where('jabatan','2');
        return $this->db->get('administrator')->result();
    }
    
    public function get_all_manager()
    {
        $this->db->select('nama,username,id');
        $this->db->where('jabatan','1');
        return $this->db->get('administrator')->row();
    }

    public function update_manager_psw($id,$new)
    {
        $this->db->where('id',$id);
        return $this->db->update('administrator',$new);
    }
    
    
    public function update_admin($id,$new)
    {
        $this->db->where('id',$id);
        return $this->db->update('administrator',$new);
    }

    public function tambah_admin($data)
    {
        return $this->db->insert('administrator',$data);
    }

    public function hapus_admin($id)
    {
        $this->db->where('id',$id);
        return $this->db->delete('administrator');
    }

    public function admin_accpengajuan($id,$id_diagnosa)
    {       
        $this->db->where('id',$id_diagnosa)->update('mesin_diagnosa', ['tgl_acc'=>date("Y-m-d")]);
         $this->db->where('id',$id);
         return $this->db->update('mesin',['status'=> '3']);

    }

}

/* End of file Dokter_model.php */