package com.indotek.mobileindotek.datamodel;


import com.google.gson.annotations.SerializedName;

public class DataNotif {
    @SerializedName("id")
    String id;

    @SerializedName("nama")
    String namaPemilik;

    @SerializedName("jenis_mesin")
    String jenisMesin;

    @SerializedName("status")
    String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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


    public String getJenisMesin() {
        return jenisMesin;
    }

    public void setJenisMesin(String jenisMesin) {
        this.jenisMesin = jenisMesin;
    }


}