package com.indotek.indotekmobileappadmin.datamodel;


import com.google.gson.annotations.SerializedName;

public class DataNotif {

    @SerializedName("id")
    String id;

    @SerializedName("id_mesin")
    String id_mesin;

    @SerializedName("nama")
    String namaPemilik;

    @SerializedName("tanggal")
    String tanggalNotif;

    @SerializedName("foto_before")
    String fotoBefore;

    @SerializedName("jenis_mesin")
    String jenisMesin;

    @SerializedName("merek")
    String merekMesin;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNamaPemilik() {
        return namaPemilik;
    }

    public void setNamaPemilik(String namaPemilik) {
        this.namaPemilik = namaPemilik;
    }

    public String getTanggalNotif() {
        return tanggalNotif;
    }

    public void setTanggalNotif(String tanggalNotif) {
        this.tanggalNotif = tanggalNotif;
    }

    public String getFotoBefore() {
        return fotoBefore;
    }

    public void setFotoBefore(String fotoBefore) {
        this.fotoBefore = fotoBefore;
    }

    public String getJenisMesin() {
        return jenisMesin;
    }

    public void setJenisMesin(String jenisMesin) {
        this.jenisMesin = jenisMesin;
    }

    public String getMerekMesin() {
        return merekMesin;
    }

    public void setMerekMesin(String merekMesin) {
        this.merekMesin = merekMesin;
    }

    public String getId_mesin() {
        return id_mesin;
    }

    public void setId_mesin(String id_mesin) {
        this.id_mesin = id_mesin;
    }
}