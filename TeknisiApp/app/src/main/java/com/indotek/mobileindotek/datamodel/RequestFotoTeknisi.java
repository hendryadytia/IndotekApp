package com.indotek.mobileindotek.datamodel;

import com.google.gson.annotations.SerializedName;

public class RequestFotoTeknisi {
    @SerializedName("code")
    String code;


    @SerializedName("data")
    UrlFotoTeknisi data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public UrlFotoTeknisi getData() {
        return data;
    }

    public void setData(UrlFotoTeknisi data) {
        this.data = data;
    }
}
