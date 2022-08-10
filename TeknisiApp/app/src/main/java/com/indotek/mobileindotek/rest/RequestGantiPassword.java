package com.indotek.mobileindotek.rest;

import com.google.gson.annotations.SerializedName;

public class RequestGantiPassword {

    @SerializedName("id")
    String id;
    @SerializedName("pwlama")
    String pwlama;
    @SerializedName("pwbaru")
    String pwbaru;

    public RequestGantiPassword(String id, String pwlama, String pwbaru) {
        this.id = id;
        this.pwlama = pwlama;
        this.pwbaru = pwbaru;
    }
}
