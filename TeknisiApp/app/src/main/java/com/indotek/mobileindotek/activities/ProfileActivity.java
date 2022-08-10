package com.indotek.mobileindotek.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.indotek.mobileindotek.R;
import com.indotek.mobileindotek.datamodel.ApiResponse;
import com.indotek.mobileindotek.datamodel.RequestFotoTeknisi;
import com.indotek.mobileindotek.datamodel.RequestUpdateMesin;
import com.indotek.mobileindotek.rest.ApiClient;
import com.indotek.mobileindotek.rest.ApiInterfaces;
import com.indotek.mobileindotek.rest.RequestGantiPassword;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    ImageView ivlogout,ivbtnback,ivuploadprofil,ivuploadworkshop,ivprofil,ivworkshop;
    LinearLayout llgantipassword,notifuploadfoto;
    TextView tvnama,tvalamat,tvtelp,tvkeahlian,tvid,tvusername,tvgantipw,tvbtngantipw;
    EditText etpwlama,etpwbaru;
    ApiInterfaces mApiInterface;
    private SharedPreferences sharedPref;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mApiInterface = ApiClient.getClient(this).create(ApiInterfaces.class);

        ivlogout = findViewById(R.id.ivlogout);
        ivbtnback = findViewById(R.id.ivbtnback);
        ivuploadprofil = findViewById(R.id.ivuploadprofil);
        ivuploadworkshop = findViewById(R.id.ivuploadworkshop);
        ivprofil = findViewById(R.id.ivprofil);
        ivworkshop = findViewById(R.id.ivworkshop);

        tvgantipw = findViewById(R.id.tvgantipw);
        tvnama = findViewById(R.id.tvnamateknisi);
        tvalamat = findViewById(R.id.tvalamat);
        tvid = findViewById(R.id.tvid);
        tvkeahlian = findViewById(R.id.tvkeahlian);
        tvtelp = findViewById(R.id.tvnotelp);
        tvusername = findViewById(R.id.tvusername);
        notifuploadfoto = findViewById(R.id.notifuploadfoto);

        llgantipassword = findViewById(R.id.llgantipassword);
        etpwlama = findViewById(R.id.etpwlama);
        etpwbaru = findViewById(R.id.etpwbaru);
        tvbtngantipw = findViewById(R.id.tvbtngantipw);

        context = this;
        sharedPref = this.getSharedPreferences(getString(R.string.namasp), Context.MODE_PRIVATE);
        ivbtnback.setOnClickListener(v->finish());
        if(sharedPref.getString(getString(R.string.jwttoken),null) == null){
            startActivity(new Intent(ProfileActivity.this,LoginActivity.class));
        }
        ivlogout.setOnClickListener(v->{

            sharedPref.edit().remove(getString(R.string.jwttoken)).apply();
            startActivity(new Intent(ProfileActivity.this,LoginActivity.class));
        });

        tvbtngantipw.setOnClickListener(v->{
            String pwlama = etpwlama.getText().toString();
            String pwbaru = etpwbaru.getText().toString();
            gantipw(pwlama,pwbaru);
        });

        tvgantipw.setOnClickListener(v->llgantipassword.setVisibility(View.VISIBLE));

        tvnama.setText(sharedPref.getString(getString(R.string.namauser), ""));
        tvalamat.setText(sharedPref.getString("alamat",""));
        tvid.setText(sharedPref.getString(getString(R.string.iduser), ""));
        tvkeahlian.setText(sharedPref.getString("keahlian",""));
        tvtelp.setText(sharedPref.getString("notelp",""));
        tvusername.setText(sharedPref.getString("username", ""));

        ivuploadprofil.setOnClickListener(v->{
            Intent i = new Intent(this, CameraImagePreview.class);
            i.putExtra("jenismesin","upprofil");
            i.putExtra("merek","");
            i.putExtra("requestcamerafor","profil");
            i.putExtra("id_mesin",sharedPref.getString(getString(R.string.iduser), ""));
            this.startActivity(i);
        });


        ivuploadworkshop.setOnClickListener(v->{
            Intent i = new Intent(this, CameraImagePreview.class);
            i.putExtra("jenismesin","upworkshop");
            i.putExtra("merek","");
            i.putExtra("id_mesin",sharedPref.getString(getString(R.string.iduser), ""));
            i.putExtra("requestcamerafor","workshop");
            this.startActivity(i);
        });

        getFotoTeknisi();

    }
    private void getFotoTeknisi(){
        Call<RequestFotoTeknisi> lm = mApiInterface.getfototeknisi(sharedPref.getString(getString(R.string.iduser), ""));
        lm.enqueue(new Callback<RequestFotoTeknisi>() {
            @Override
            public void onResponse(Call<RequestFotoTeknisi> call, Response<RequestFotoTeknisi> response) {
                if(response.isSuccessful()){
                    if (response.body().getCode().equals("200")){
                        boolean checkFotoProfil = response.body().getData().getFoto_diri().isEmpty();
                        boolean checkFotoWorkshop = response.body().getData().getFoto_workshop().isEmpty();

                        ivuploadprofil.setVisibility(View.GONE);
                        ivuploadworkshop.setVisibility(View.GONE);
                        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.linkapi),MODE_PRIVATE);
                        String baseurl = sharedPreferences.getString(getString(R.string.linkapi)," ");

                        if(!checkFotoProfil){
                        Picasso.get().load(baseurl+"/"+response.body().getData().getFoto_diri()).into(ivprofil);
                        }else{
                            ivuploadprofil.setVisibility(View.VISIBLE);

                        }


                        if(!checkFotoWorkshop) {
                            Picasso.get().load(baseurl + "/"+response.body().getData().getFoto_workshop()).into(ivworkshop);
                        }else{
                            ivuploadworkshop.setVisibility(View.VISIBLE);
                        }

                        if(!checkFotoProfil && !checkFotoWorkshop){
                            notifuploadfoto.setVisibility(View.GONE);
                        }

                    }else{
                        Log.d("TAG", "onResponse: " + response.body().getCode());
                    }

                }else{
                    Toast.makeText(context, "terjadi error, mohon refresh", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<RequestFotoTeknisi> call, Throwable t) {
                Log.d("TAG", "onFailure: " + t);

            }
        });
    }

    private void gantipw(String pwlama, String pwbaru){
        String id = sharedPref.getString(getString(R.string.iduser),"");
        Call<ApiResponse> lm = mApiInterface.gantipw(new RequestGantiPassword(id,pwlama,pwbaru));
        if(!pwbaru.isEmpty()){
            lm.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    if(response.isSuccessful()){
                        if (response.body().getCode().equals("200")){
                            llgantipassword.setVisibility(View.GONE);
                            Toast.makeText(context, "Berhasil Ganti Password", Toast.LENGTH_SHORT).show();
                        }else{
                            Log.d("TAG", "onResponse: " + response.body().getCode());
                            Toast.makeText(context, "Password Lama yang anda masukkan salah", Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        Toast.makeText(context, "terjadi error, mohon coba lagi", Toast.LENGTH_SHORT).show();
                    }


                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    Log.d("TAG", "onFailure: " + t);

                }
            });
        }
        else{
            Toast.makeText(context, "Mohon Isi Password Baru", Toast.LENGTH_SHORT).show();   
        }
       
    }

    @Override
    protected void onResume() {
        super.onResume();
        Handler handler = new Handler();
        handler.postDelayed(this::getFotoTeknisi,3000);
    }


    private int backPressedCount;
    @Override
    public void onBackPressed() {
        if(backPressedCount == 1 || llgantipassword.getVisibility() == View.GONE){
            finish();
        }else{
            ++backPressedCount;
            llgantipassword.setVisibility(View.GONE);
        }
    }
}