package com.indotek.indotekmobileappadmin.datamodel;

import com.google.gson.annotations.SerializedName;

public class LoginModel {
    @SerializedName("data")
    dataakunadmin data;

    @SerializedName("password")
    String password;

    @SerializedName("username")
    String username;

    @SerializedName("token")
    String token;

    @SerializedName("code")
    String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNama(){
        return data.getNama();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return data.getToken();
    }

    public void setToken(String token) {
        this.token = token;
    }
}

class dataakunadmin{
    String token,nama;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}
