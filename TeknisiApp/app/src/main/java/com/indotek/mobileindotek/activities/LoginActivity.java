package com.indotek.mobileindotek.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.indotek.mobileindotek.R;
import com.indotek.mobileindotek.datamodel.LoginModel;
import com.indotek.mobileindotek.rest.ApiClient;
import com.indotek.mobileindotek.rest.ApiInterfaces;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private TextView btnlogin,btnregister,savelinkapi,tvjudul;
    private EditText username,password,linkapi;
    private String dtusername,dtpassword;
    private ApiInterfaces mApiInterface;
    private LinearLayout submitlinkapill;
    private View tombolrahasia;
    private ImageView btnPaste;
    Context context;
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnlogin = findViewById(R.id.btnlogin);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        linkapi = findViewById(R.id.linkapi);
        savelinkapi = findViewById(R.id.savelinkapi);
        submitlinkapill = findViewById(R.id.submitlinkapi);
        tombolrahasia = findViewById(R.id.tombolrahasia);
        btnPaste = findViewById(R.id.btnPaste);
        tvjudul = findViewById(R.id.tvjudul);
        context = this;

        tombolrahasia.setOnClickListener(v->{
            submitlinkapill.setVisibility(View.VISIBLE);
        });

        tvjudul.setOnClickListener(v -> {
            sharedPref.edit().remove(getString(R.string.linkapi)).apply();
            Toast.makeText(context, "reset link api", Toast.LENGTH_SHORT).show();
        });


        btnPaste.setOnClickListener(v->{
            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            if (clipboard == null) return;
            ClipData clip = clipboard.getPrimaryClip();
            if (clip == null) return;
            ClipData.Item item = clip.getItemAt(0);
            if (item == null) return;
            CharSequence textToPaste = item.getText();
            if (textToPaste == null) return;

            linkapi.setText(textToPaste);
        });
        SharedPreferences pointerLinkApi = context.getSharedPreferences(getString(R.string.linkapi),Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pointerLinkApi.edit();

        String link = "https://api-indotek.masuk.id/api/";
        editor.putString(getString(R.string.linkapi), link);
        editor.apply();


        savelinkapi.setOnClickListener(v->{

            String linkbaru = linkapi.getText().toString();
            if (linkbaru.length() > 7 ){
                String check1 = linkbaru.substring(0,7);//http://
                String check2 = linkbaru.substring(0,8);//https://
                if(check1.equals("http://")  || check2.equals("https://")){
                    editor.putString(getString(R.string.linkapi), linkbaru);
                    editor.apply();
                    ngeping();
                    submitlinkapill.setVisibility(View.GONE);
                }else{
                    Toast.makeText(context, "Link harus menyertakan http:// atau https://", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(context, "Link API salah", Toast.LENGTH_SHORT).show();

            }

        });


        sharedPref = context.getSharedPreferences(getString(R.string.namasp), Context.MODE_PRIVATE);

        btnlogin.setOnClickListener(v-> {
                    dtusername = username.getText().toString();
                    dtpassword = password.getText().toString();
                    menglogin(dtusername,dtpassword);
                }
        );

    }

    private void menglogin(String usr, String pswd){
        LoginModel loginModel = new LoginModel();
        loginModel.setPassword(pswd);
        loginModel.setUsername(usr);
        mApiInterface = ApiClient.getClient(this).create(ApiInterfaces.class);
        Toast.makeText(context, "Login....", Toast.LENGTH_SHORT).show();

//        Intent i = new Intent(MainActivity.this,Homescreen.class);
//        startActivity(i);

        Call<LoginModel> lm = mApiInterface.loginin(loginModel);
        lm.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if(response.isSuccessful()){
                    if (response.body().getCode().equals("200")){
                        LoginModel data = response.body();
                        Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString(getString(R.string.jwttoken), data.getToken());
                        editor.putString(getString(R.string.namauser), data.getNama());
                        editor.putString("alamat",data.getAlamat());
                        editor.putString("keahlian",data.getKeahlian());
                        editor.putString("notelp",data.getNoTelp());
                        editor.putString("username", data.getUsername());
                        editor.putString(getString(R.string.iduser), data.getId());
                        editor.apply();
                        startActivity(i);
                    }else{
                        Toast.makeText(context, "Password/Username Salah", Toast.LENGTH_SHORT).show();

                    }

                }else{
                    Toast.makeText(context, "mohon update link API", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error, mohon update link API" + t, Toast.LENGTH_LONG).show();

            }
        });
    }

    private void ngeping(){
        LoginModel loginModel = new LoginModel();
        mApiInterface = ApiClient.getClient(this).create(ApiInterfaces.class);

        Call<LoginModel> lm = mApiInterface.ngeping();
        lm.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if(response.isSuccessful()){
                    if (response.body().getCode().equals("444")){
                        LoginModel data = response.body();
                        Toast.makeText(getApplicationContext(), "PONG!", Toast.LENGTH_LONG).show();
                    }



                }else{
                    Toast.makeText(getApplicationContext(), "link salah ", Toast.LENGTH_LONG).show();

                }


            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error, mohon update link API" + t, Toast.LENGTH_LONG).show();

            }
        });
    }

    @Override
    public void onBackPressed() {
            submitlinkapill.setVisibility(View.GONE);
    }


}