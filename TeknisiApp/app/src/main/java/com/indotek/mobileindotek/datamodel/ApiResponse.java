package com.indotek.mobileindotek.datamodel;

import com.google.gson.annotations.SerializedName;

public class ApiResponse {
    @SerializedName("code")
    String code;

    @SerializedName("message")
    String message;

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
}

