package com.indotek.indotekmobileappadmin.rest;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Toast;

import com.indotek.indotekmobileappadmin.R;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit = null;



//    public static final String BASE_URL = "http://0fdf-114-124-213-95.ngrok.io/user/";
    public static Retrofit getClient(Context context) {

        String ddefault="";
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.linkapi), Context.MODE_PRIVATE);
        SharedPreferences sharedPref2 = context.getSharedPreferences(context.getString(R.string.namasp), Context.MODE_PRIVATE);

        String JWT= sharedPref2.getString(context.getString(R.string.jwttoken),"");
        String linkapi = sharedPref.getString(context.getString(R.string.linkapi),ddefault);

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request newRequest = chain.request().newBuilder()
                        .addHeader("Authorization",JWT)
                        .build();
                return chain.proceed(newRequest);
            }
        }).build();


        if (linkapi.length() > 7 ){
            String check1 = linkapi.substring(0,7);//http://
            String check2 = linkapi.substring(0,8);//https://
            if(check1.equals("http://")  || check2.equals("https://")){
                retrofit = new Retrofit.Builder()
                        .client(client)
                        .baseUrl(linkapi)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }else{
                Toast.makeText(context, "Link API salah", Toast.LENGTH_SHORT).show();
            }
        }else{
            retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl("http://localhost")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            Toast.makeText(context, "Link API salah", Toast.LENGTH_SHORT).show();

        }



        return retrofit;
    }
}
