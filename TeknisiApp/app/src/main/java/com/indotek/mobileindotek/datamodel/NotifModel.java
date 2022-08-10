package com.indotek.mobileindotek.datamodel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NotifModel {

    @SerializedName("code")
    String code;

    @SerializedName("message")
    String message;

    @SerializedName("data")
    List<DataNotif> data;

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

    public List<DataNotif> getData() {
        return data;
    }

    public void setData(List<DataNotif> data) {
        this.data = data;
    }
}
