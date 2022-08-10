package com.indotek.indotekmobileappadmin.rest;


import com.indotek.indotekmobileappadmin.datamodel.ApiResponse;
import com.indotek.indotekmobileappadmin.datamodel.LoginModel;
import com.indotek.indotekmobileappadmin.datamodel.NotifModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterfaces {

    @POST("mobileapp/login")
    Call<LoginModel> loginin(@Body LoginModel loginModel);

    @GET("mobileapp/ping")
    Call<LoginModel> ngeping();

    @GET("mobileapp/ceknotif")
    Call<NotifModel> getnotif();

    @Multipart
    @POST("mobileapp/uploadfotomesin")
    Call<ApiResponse> uploadImage(@Part("file\"; fileName=\"myFile.png\" ") RequestBody file, @Part("idmesin") RequestBody idmesin, @Part("beforaf") String before );



}
