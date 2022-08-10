package com.indotek.mobileindotek.datamodel;

import com.google.gson.annotations.SerializedName;

public class RequestCekNotif {
    @SerializedName("id")
    String id;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
