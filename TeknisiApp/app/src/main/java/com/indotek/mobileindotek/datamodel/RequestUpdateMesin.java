package com.indotek.mobileindotek.datamodel;

import com.google.gson.annotations.SerializedName;

public class RequestUpdateMesin {
    @SerializedName("estimasi")
    String estimasi;

    @SerializedName("id")
    String id;

    @SerializedName("id_diagnosa")
    String id_diagnosa;

    @SerializedName("id_teknisi")
    String idteknisi;

    @SerializedName("biaya")
    String biaya;

    @SerializedName("diagnosa")
    String diagnosa;

    @SerializedName("status")
    String status;

    public RequestUpdateMesin(String estimasi, String id, String biaya, String diagnosa,String id_diagnosa, String status,String idteknisi) {
        this.estimasi = estimasi;
        this.id = id;
        this.biaya = biaya;
        this.id_diagnosa = id_diagnosa;
        this.diagnosa = diagnosa;
        this.status = status;
        this.idteknisi = idteknisi;
    }
}
