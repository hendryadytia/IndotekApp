package com.indotek.mobileindotek.datamodel;

import com.google.gson.annotations.SerializedName;

public class ResponseGetDataMesin {


    @SerializedName("code")
    String code;

    @SerializedName("message")
    String message;

    @SerializedName("data")
    DataMesin data;




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

    public DataMesin getData() {
        return data;
    }

    public void setData(DataMesin data) {
        this.data = data;
    }
}
