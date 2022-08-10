package com.indotek.mobileindotek.datamodel;

import com.google.gson.annotations.SerializedName;

public class LoginModel {
    @SerializedName("data")
    dataakunteknisi data;

    @SerializedName("password")
    String password;

    @SerializedName("username")
    String username;

    @SerializedName("id")
    String iduser;

    @SerializedName("token")
    String token;

    @SerializedName("code")
    String code;

    @SerializedName("alamat")
    String alamat;

    @SerializedName("keahlian")
    String keahlian;

    @SerializedName("no_telp")
    String noTelp;


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
        return data.username;
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

    public String getId() {
        return data.getId();
    }

    public String getAlamat() {
        return data.alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getKeahlian() {
        return data.keahlian;
    }

    public void setKeahlian(String keahlian) {
        this.keahlian = keahlian;
    }

    public String getNoTelp() {
        return data.no_telp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

}

class dataakunteknisi {
    String token,nama,id,alamat,keahlian,no_telp,username;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNama() {
        return nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getKeahlian() {
        return keahlian;
    }

    public void setKeahlian(String keahlian) {
        this.keahlian = keahlian;
    }

    public String getNoTelp() {
        return no_telp;
    }

    public void setNoTelp(String noTelp) {
        this.no_telp = noTelp;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}
