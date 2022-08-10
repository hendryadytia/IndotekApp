package com.indotek.mobileindotek.datamodel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseCekNotif {
    @SerializedName("code")
    String code;

    @SerializedName("message")
    String message;

    @SerializedName("data")
    List<ItemRVUtamaModel> object;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ItemRVUtamaModel> getObject() {
        return object;
    }

    public void setObject(List<ItemRVUtamaModel> object) {
        this.object = object;
    }
}
