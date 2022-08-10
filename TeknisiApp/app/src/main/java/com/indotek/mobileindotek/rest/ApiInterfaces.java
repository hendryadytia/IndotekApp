package com.indotek.mobileindotek.rest;

import com.indotek.mobileindotek.datamodel.ApiResponse;
import com.indotek.mobileindotek.datamodel.NotifModel;
import com.indotek.mobileindotek.datamodel.RequestFotoTeknisi;
import com.indotek.mobileindotek.datamodel.RequestUpdateMesin;
import com.indotek.mobileindotek.datamodel.ResponseGetDataMesin;
import com.indotek.mobileindotek.datamodel.LoginModel;
import com.indotek.mobileindotek.datamodel.ResponseCekNotif;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiInterfaces {

    @POST("mobileapp/loginteknisi")
    Call<LoginModel> loginin(@Body LoginModel loginModel);

    @POST("mobileapp/teknisiupdatemesin")
    Call<ApiResponse> updatemesin(@Body RequestUpdateMesin requestUpdateMesin);

    @POST("mobileapp/teknisigantipw")
    Call<ApiResponse> gantipw(@Body RequestGantiPassword requestUpdateMesin);

    @GET("mobileapp/ping")
    Call<LoginModel> ngeping();

    @GET("mobileapp/ceknotif")
    Call<ResponseCekNotif> getnotif(@Query("id") String id,@Query("status") String status);

    @GET("mobileapp/ceknotif")
    Call<NotifModel> getnotifbackground(@Query("id") String id, @Query("status") String status);

    @GET("mobileapp/geturlfototeknisi")
    Call<RequestFotoTeknisi> getfototeknisi(@Query("id") String id);

    @GET("mobileapp/detailmesin")
    Call<ResponseGetDataMesin> getdatamesin(@Query("id") String id, @Query("status") String status);

    @Multipart
    @POST("mobileapp/uploadfotomesin")
    Call<ApiResponse> uploadImage(@Part("file\"; fileName=\"myFile.png\" ") RequestBody file, @Part("idmesin") RequestBody idmesin, @Part("beforaf") String before );

    @Multipart
    @POST("mobileapp/teknisiupdatefoto")
    Call<ApiResponse> uploadFotoTeknisi(@Part("file\"; fileName=\"myFile.png\" ") RequestBody file, @Part("id_teknisi") RequestBody idteknisi, @Part("fotountuk") String fotountuk );

}
