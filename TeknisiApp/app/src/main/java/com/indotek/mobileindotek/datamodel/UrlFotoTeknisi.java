package com.indotek.mobileindotek.datamodel;

import com.google.gson.annotations.SerializedName;

public class UrlFotoTeknisi {
    @SerializedName("foto_diri")
    String foto_diri;

    @SerializedName("foto_workshop")
    String foto_workshop;


    public String getFoto_diri() {
        return foto_diri;
    }

    public void setFoto_diri(String foto_diri) {
        this.foto_diri = foto_diri;
    }

    public String getFoto_workshop() {
        return foto_workshop;
    }

    public void setFoto_workshop(String foto_workshop) {
        this.foto_workshop = foto_workshop;
    }
}
