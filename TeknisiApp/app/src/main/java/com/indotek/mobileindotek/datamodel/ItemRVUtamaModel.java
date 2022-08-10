package com.indotek.mobileindotek.datamodel;

import com.google.gson.annotations.SerializedName;

public class ItemRVUtamaModel {
    @SerializedName("id_notif")
    String id;

    @SerializedName("id_mesin")
    String idMesin;

    @SerializedName("jenis_mesin")
    String jenis;

    @SerializedName("keterangan")
    String keluhan;

    @SerializedName("status")
    String status;

    @SerializedName("tanggal")
    String tanggal;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdMesin() {
        return idMesin;
    }

    public void setIdMesin(String idMesin) {
        this.idMesin = idMesin;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getKeluhan() {
        return keluhan;
    }

    public void setKeluhan(String keluhan) {
        this.keluhan = keluhan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
}
