package com.indotek.mobileindotek.datamodel;


import com.google.gson.annotations.SerializedName;

public class DataMesin{

    @SerializedName("id_mesin")
    String idMesin;

    @SerializedName("id_diagnosa")
    String idDiagnosa;

    @SerializedName("jenis_mesin")
    String jenis;

    @SerializedName("foto_before")
    String fotoBefore;

    @SerializedName("foto_after")
    String fotoAfter;

    @SerializedName("keterangan")
    String keluhan;

    @SerializedName("merek")
    String merek;

    @SerializedName("diagnosa")
    String diagnosa;

    @SerializedName("biaya")
    String biaya;

    @SerializedName("status")
    String status;

    @SerializedName("est_selesai")
    String estimasi;

    @SerializedName("tanggal")
    String tanggal;

    public String getEstimasi() {
        return estimasi;
    }

    public void setEstimasi(String estimasi) {
        this.estimasi = estimasi;
    }

    public String getIdMesin() {
        return idMesin;
    }

    public void setIdMesin(String idMesin) {
        this.idMesin = idMesin;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getFotoBefore() {
        return fotoBefore;
    }

    public void setFotoBefore(String fotoBefore) {
        this.fotoBefore = fotoBefore;
    }

    public String getFotoAfter() {
        return fotoAfter;
    }

    public void setFotoAfter(String fotoAfter) {
        this.fotoAfter = fotoAfter;
    }

    public String getKeluhan() {
        return keluhan;
    }

    public void setKeluhan(String keluhan) {
        this.keluhan = keluhan;
    }

    public String getMerek() {
        return merek;
    }

    public void setMerek(String merek) {
        this.merek = merek;
    }

    public String getDiagnosa() {
        return diagnosa;
    }

    public void setDiagnosa(String diagnosa) {
        this.diagnosa = diagnosa;
    }

    public String getBiaya() {
        return biaya;
    }

    public void setBiaya(String biaya) {
        this.biaya = biaya;
    }

    public String getStatus() {
        return status;
    }

    public String getIdDiagnosa() {
        return idDiagnosa;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTanggal() {
        return tanggal;
    }
    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
}
