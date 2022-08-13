<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class Notifikasi_model extends CI_Model {


    public function get_notifikasiteknisi($id, $status)
    {
        $this->db->select('mesin.id,mesin.tgl_masuk as tanggal,mesin.id as id_mesin,mesin.jenis_mesin,mesin.status,mesin.keterangan');
        $this->db->from('mesin_kepemilikan');
        $this->db->join('mesin','mesin.id = mesin_kepemilikan.id_mesin');
        $this->db->order_by("mesin.id", "desc");
        $this->db->where('mesin_kepemilikan.id_teknisi', $id);
        $this->db->where('mesin.status', $status);
        return $this->db->get()->result();
    }
    public function get_notifikasiteknisibackground($id)
    {
        $this->db->select('mesin.id,mesin.tgl_masuk as tanggal,mesin.id as id_mesin,mesin.jenis_mesin,mesin.status,mesin.keterangan');
        $this->db->from('mesin_kepemilikan');
        $this->db->join('mesin','mesin.id = mesin_kepemilikan.id_mesin');
        $this->db->order_by("mesin.id", "desc");
        $this->db->where('mesin_kepemilikan.id_teknisi', $id);
        $this->db->where("(mesin.status = '0' OR mesin.status = '3')");

        return $this->db->get()->result();
    }
    public function get_notifikasiteknisiproses($id)
    {
        $this->db->select('mesin.id,mesin.tgl_masuk as tanggal,mesin.id as id_mesin,mesin.jenis_mesin,mesin.status,mesin.keterangan');
        $this->db->from('mesin_kepemilikan');
        $this->db->join('mesin','mesin.id = mesin_kepemilikan.id_mesin');
        $this->db->order_by("mesin.id", "desc");
        $this->db->where('mesin_kepemilikan.id_teknisi', $id);
        $this->db->where('mesin.status !=', "0");

        return $this->db->get()->result();
    }
    
    public function get_notifikasirequestacc()
    {
        $this->db->select('notifikasi.id,notifikasi.tanggal,mesin.id as id_mesin,mesin.jenis_mesin,mesin.status,mesin.keluhan');
        $this->db->from('notifikasi');
        $this->db->join('mesin','mesin.id = notifikasi.id_mesin');
        $this->db->order_by("id", "desc");
        $this->db->where('mesin.status', "2");

        return $this->db->get()->result();
    }

    // public function get_allnotifikasi($iduser)
    // {
    //     $this->db->where('id_tertuju', $iduser);
    //     return $this->db->get('notifikasi')->result();
    // }
    
    public function get_allnotifikasirequestfoto()
    {
        $this->db->select('mesin.tgl_masuk,mesin.foto_before,mesin.merek,mesin.id as id_mesin,mesin.jenis_mesin,pelanggan.nama');
        $this->db->from('mesin');
        $this->db->join('mesin_kepemilikan','mesin_kepemilikan.id_mesin = mesin.id');
        $this->db->join('pelanggan','pelanggan.id = mesin_kepemilikan.id_pelanggan');
        $this->db->order_by("mesin.id", "desc");
        return $this->db->get()->result();
    }
    


    public function hapus_notifikasi($id)
    {
      
    }

    public function get_notifdashboard()
    {
        $this->db->select('mesin.jenis_mesin,mesin.id as id_mesin, pelanggan.nama, mesin_diagnosa.id as id_diagnosa, mesin_diagnosa.tgl_pengajuan');
        $this->db->from('mesin_diagnosa');
        $this->db->join('mesin','mesin.id = mesin_diagnosa.id_mesin');
        $this->db->join('mesin_kepemilikan','mesin_kepemilikan.id_mesin = mesin_diagnosa.id_mesin');
        $this->db->join('pelanggan','pelanggan.id = mesin_kepemilikan.id_pelanggan');
        $this->db->where('mesin.status','2');
        $this->db->order_by("mesin_diagnosa.id", "desc");
        return $this->db->get()->result();
    }

    public function get_detailnotifdashboard($id)
    {
        $this->db->select('mesin.jenis_mesin, pelanggan.nama, mesin.id as id_mesin, mesin_diagnosa.tgl_pengajuan,mesin_diagnosa.diagnosa,mesin_diagnosa.biaya,mesin_diagnosa.est_selesai,teknisi.id as id_teknisi,teknisi.nama as nama_teknisi');
        $this->db->from('mesin_diagnosa');
        $this->db->join('mesin_kepemilikan','mesin_kepemilikan.id_mesin = mesin_diagnosa.id_mesin');
        $this->db->join('mesin','mesin.id = mesin_diagnosa.id_mesin');
        $this->db->join('teknisi','teknisi.id = mesin_diagnosa.id_teknisi');
        $this->db->join('pelanggan','pelanggan.id = mesin_kepemilikan.id_pelanggan');
        $this->db->where('mesin_diagnosa.id',$id);
        return $this->db->get()->row();
    }

}

/* End of file Dokter_model.php */