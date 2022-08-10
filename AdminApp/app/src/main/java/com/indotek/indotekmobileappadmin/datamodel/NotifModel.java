package com.indotek.indotekmobileappadmin.datamodel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NotifModel {


    @SerializedName("data")
    List<DataNotif> dataNotif;

    @SerializedName("code")
    String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<DataNotif> dataNotifgetDataNotif() {
        return dataNotif;
    }

    public void setDataNotif(List<DataNotif> dataNotif) {
        this.dataNotif = dataNotif;
    }



}
