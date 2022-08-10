package com.indotek.mobileindotek.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.indotek.mobileindotek.R;
import com.indotek.mobileindotek.datamodel.ApiResponse;
import com.indotek.mobileindotek.datamodel.DataMesin;
import com.indotek.mobileindotek.datamodel.RequestUpdateMesin;
import com.indotek.mobileindotek.datamodel.ResponseGetDataMesin;
import com.indotek.mobileindotek.rest.ApiClient;
import com.indotek.mobileindotek.rest.ApiInterfaces;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static android.view.View.GONE;

public class DetailMesinActivity extends AppCompatActivity {
    private TextView tvmerek,tvjenis,tvketerangan,tvdiagnosa,tvbiaya,plivbefore,tvbtnivafter,
    tvlabelselesai,tvlabeldiagnosa,tvlabelbiaya,tvbtnaksi,tvestselesai;
    private String idmesin,baseurl,estselesai,status,idteknisi,idDiagnosa;
    private LinearLayout llloading,llestselesai;
    private EditText etdiagnosa,etbiaya;
    private SharedPreferences sharedPref;
    private ApiInterfaces mApiInterface;
    private ImageView logoanim,ivbefore,ivafter,ivbtnback;
    private AnimatedVectorDrawable anim;
    private CardView cv1hari,cv2hari,cv3hari,cvdiagnosa,cvbiaya;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_mesin);
        SharedPreferences sharedPreferences =  this.getSharedPreferences(this.getString(R.string.linkapi), Context.MODE_PRIVATE);

        baseurl = sharedPreferences.getString(this.getString(R.string.linkapi)," ");
        sharedPref = this.getSharedPreferences(getString(R.string.namasp), Context.MODE_PRIVATE);
        idteknisi = sharedPref.getString(getString(R.string.iduser), "");
        tvmerek = findViewById(R.id.tvmerek);
        tvjenis = findViewById(R.id.tvjenis);
        tvketerangan = findViewById(R.id.tvketerangan);
        tvdiagnosa = findViewById(R.id.tvdiagnosa);
        tvbiaya = findViewById(R.id.tvbiaya);
        etdiagnosa = findViewById(R.id.etdiagnosa);
        etbiaya = findViewById(R.id.etbiaya);
        llloading = findViewById(R.id.llloading);
        ivbefore = findViewById(R.id.ivbefore);
        ivafter = findViewById(R.id.ivafter);
        plivbefore = findViewById(R.id.plivbefore);
        tvbtnivafter = findViewById(R.id.tvbtnivafter);
        tvlabelselesai = findViewById(R.id.tvlabelselesai);
        tvlabeldiagnosa = findViewById(R.id.tvlabeldiagnosa);
        tvlabelbiaya = findViewById(R.id.tvlabelbiaya);
        llestselesai = findViewById(R.id.llestselesai);
        tvbtnaksi = findViewById(R.id.tvbtnaksi);
        tvestselesai = findViewById(R.id.tvestselesai);
        cvdiagnosa = findViewById(R.id.cvdiagnosa);
        cvbiaya = findViewById(R.id.cvbiaya);
        cv1hari = findViewById(R.id.cv1hari);
        cv2hari= findViewById(R.id.cv2hari);
        cv3hari= findViewById(R.id.cv3hari);
        ivbtnback = findViewById(R.id.ivbtnback);


        ivbtnback.setOnClickListener(v->finish());


        idmesin = getIntent().getStringExtra("idmesin");
        status = getIntent().getStringExtra("statusmesin");

        logoanim = findViewById(R.id.logoanim);
        anim = (AnimatedVectorDrawable) logoanim.getDrawable();

        tvbtnaksi.setOnClickListener(v->updateDataMesin());

        anim.start();


        getDataMesin();
    }

    private void requestacc(Boolean fotoBeforeAvail, DataMesin d){

        tvmerek.setText(d.getMerek());
        tvjenis.setText(d.getJenis());
        tvketerangan.setText(d.getKeluhan());

        tvlabeldiagnosa.setVisibility(GONE);
        tvdiagnosa.setVisibility(GONE);
        cvdiagnosa.setVisibility(GONE);


        tvlabelbiaya.setVisibility(GONE);
        tvbiaya.setVisibility(GONE);
        cvbiaya.setVisibility(GONE);

        tvlabelselesai.setVisibility(GONE);
        tvestselesai.setVisibility(GONE);
        llestselesai.setVisibility(GONE);

        tvbtnivafter.setVisibility(GONE);
        ivafter.setVisibility(GONE);

        tvbtnaksi.setText("Terima");

        status = "1";


        if(!fotoBeforeAvail){
            plivbefore.setVisibility(GONE);
            Picasso.get().load(baseurl+"/"+d.getFotoBefore()).into(ivbefore);
        }

    }

    private void requestdiagnosa(boolean fotoBeforeAvail,DataMesin d){
        tvmerek.setText(d.getMerek());
        tvjenis.setText(d.getJenis());
        tvketerangan.setText(d.getKeluhan());
        idDiagnosa = d.getIdDiagnosa();
        tvdiagnosa.setVisibility(GONE);

        tvbiaya.setVisibility(GONE);

        tvestselesai.setVisibility(GONE);
        cv1hari.setOnClickListener(v->{
            cv1hari.setCardBackgroundColor(this.getResources().getColor(R.color.maroon));
            cv2hari.setCardBackgroundColor(this.getResources().getColor(R.color.white));
            cv3hari.setCardBackgroundColor(this.getResources().getColor(R.color.white));
            estselesai = "1";
        });
        cv2hari.setOnClickListener(v->{
            cv1hari.setCardBackgroundColor(this.getResources().getColor(R.color.white));
            cv2hari.setCardBackgroundColor(this.getResources().getColor(R.color.maroon));
            cv3hari.setCardBackgroundColor(this.getResources().getColor(R.color.white));
            estselesai = "2";
        });
        cv3hari.setOnClickListener(v->{
            cv1hari.setCardBackgroundColor(this.getResources().getColor(R.color.white));
            cv2hari.setCardBackgroundColor(this.getResources().getColor(R.color.white));
            cv3hari.setCardBackgroundColor(this.getResources().getColor(R.color.maroon));
            estselesai = "3";
        });

        status = "2";


        tvbtnaksi.setText("Ajukan");


        tvbtnivafter.setVisibility(GONE);
        ivafter.setVisibility(GONE);

        if(!fotoBeforeAvail){
            plivbefore.setVisibility(GONE);
            Picasso.get().load(baseurl+"/"+d.getFotoBefore()).into(ivbefore);
        }

    }


    private void blmaccbiaya(boolean fotoBeforeAvail, DataMesin d){
        tvmerek.setText(d.getMerek());
        tvjenis.setText(d.getJenis());
        tvketerangan.setText(d.getKeluhan());
        tvestselesai.setText(d.getEstimasi() + " hari");
        tvdiagnosa.setText(d.getDiagnosa());
        tvbiaya.setText(d.getBiaya());

        cvdiagnosa.setVisibility(GONE);
        cvbiaya.setVisibility(GONE);
        llestselesai.setVisibility(GONE);

        tvbtnivafter.setVisibility(GONE);
        ivafter.setVisibility(GONE);
        if(!fotoBeforeAvail){
            plivbefore.setVisibility(GONE);
            Picasso.get().load(baseurl+"/"+d.getFotoBefore()).into(ivbefore);
        }

        tvbtnaksi.setText("Menunggu Acc...");
        tvbtnaksi.setBackgroundColor(this.getResources().getColor(R.color.darkgray));

    }


    private void requestkabarselesai(boolean fotoBeforeAvail, boolean fotoAfterAvail,DataMesin d){
        tvmerek.setText(d.getMerek());
        tvjenis.setText(d.getJenis());
        tvketerangan.setText(d.getKeluhan());
        tvestselesai.setText(d.getEstimasi() + " hari");
        tvdiagnosa.setText(d.getDiagnosa());
        tvbiaya.setText(d.getBiaya());


        cvdiagnosa.setVisibility(GONE);
        cvbiaya.setVisibility(GONE);
        llestselesai.setVisibility(GONE);

        tvbtnivafter.setOnClickListener(v->{
            Intent i = new Intent(this, CameraImagePreview.class);
            i.putExtra("jenismesin",d.getJenis());
            i.putExtra("merek",d.getMerek());
            i.putExtra("id_mesin",idmesin);
            i.putExtra("requestcamerafor","updatemsin");

            this.startActivity(i);
        });

        if(!fotoBeforeAvail){
            plivbefore.setVisibility(GONE);
            Picasso.get().load(baseurl+"/"+d.getFotoBefore()).into(ivbefore);
        }

        if(!fotoAfterAvail){
            Picasso.get().load(baseurl+"/"+d.getFotoAfter()).into(ivafter);
            tvbtnivafter.setVisibility(GONE);
        }
        status = "4";



        tvbtnaksi.setText("Kabari Selesai");

    }

 private void sudahselesai(boolean fotoBeforeAvail, boolean fotoAfterAvail,DataMesin d){
        tvmerek.setText(d.getMerek());
        tvjenis.setText(d.getJenis());
        tvketerangan.setText(d.getKeluhan());
         tvestselesai.setText(d.getEstimasi() + " hari");
         tvdiagnosa.setText(d.getDiagnosa());
         tvbiaya.setText(d.getBiaya());


        cvdiagnosa.setVisibility(GONE);
        cvbiaya.setVisibility(GONE);
        llestselesai.setVisibility(GONE);

        if(!fotoBeforeAvail){
            plivbefore.setVisibility(GONE);
            Picasso.get().load(baseurl+"/"+d.getFotoBefore()).into(ivbefore);
        }
         if(!fotoAfterAvail){
             Picasso.get().load(baseurl+"/"+d.getFotoAfter()).into(ivafter);
         }
             tvbtnivafter.setVisibility(GONE);
             status = "4";
        tvbtnaksi.setVisibility(GONE);

    }



    private void getDataMesin(){

        mApiInterface = ApiClient.getClient(this).create(ApiInterfaces.class);
        Call<ResponseGetDataMesin> lm = mApiInterface.getdatamesin(idmesin,status);
        lm.enqueue(new Callback<ResponseGetDataMesin>() {
            @Override
            public void onResponse(Call<ResponseGetDataMesin> call, Response<ResponseGetDataMesin> response) {
                if(response.isSuccessful()){
                    if (response.body().getCode().equals("200")){
                        anim.stop();
                        llloading.setVisibility(GONE);
                        DataMesin d = response.body().getData();
                        boolean fotobefore = d.getFotoBefore() == null;
                        boolean fotoafter = d.getFotoAfter() == null;
                        switch (d.getStatus()){
                            case "1":
                                requestdiagnosa(fotobefore,d);
                                break;
                            case "2":
                                blmaccbiaya(fotobefore,d);
                                break;
                            case "3":
                                requestkabarselesai(fotobefore,fotoafter,d);
                                break;
                            case "4":
                                sudahselesai(fotobefore,fotoafter,d);
                                break;
                            default:
                                requestacc(fotobefore,d);
                                break;



                        }


                    }else{
                        Log.d("TAG", "onResponse: " + response.body().getCode() + response.body().getMessage());
                    }

                }else{
                    Toast.makeText(DetailMesinActivity.this, "terjadi error, mohon refresh", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<ResponseGetDataMesin> call, Throwable t) {
                Log.d("TAG", "onFailure: " + t);

            }
        });
    }

    private void updateDataMesin(){

        String diagnosa = etdiagnosa.getText().toString();
        String biaya = etbiaya.getText().toString();


        if(status.equals("2")){
            boolean cekDiagnosa,cekBiaya,cekEst;
            cekDiagnosa = diagnosa.isEmpty();
            cekBiaya = biaya.isEmpty();
            cekEst = estselesai == null;
            if(!cekDiagnosa && !cekBiaya && !cekEst){
                RequestUpdateMesin r = new RequestUpdateMesin(estselesai,idmesin,biaya,diagnosa,idDiagnosa,status,idteknisi);
                Log.d(TAG, "updateDataMesin: "+  biaya+ diagnosa+ idDiagnosa+ status);
                mApiInterface = ApiClient.getClient(this).create(ApiInterfaces.class);
                Call<ApiResponse> lm = mApiInterface.updatemesin(r);
                lm.enqueue(new Callback<ApiResponse>() {
                    @Override
                    public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                        if(response.isSuccessful()){
                            if (response.body().getCode().equals("200")){
                                Toast.makeText(DetailMesinActivity.this, "Berhasil Kirim Diagnosa Mesin", Toast.LENGTH_SHORT).show();
                                finish();
                            }else{
                                Log.d("TAG", "onResponse: " + response.body().getCode() + response.body().getMessage());
                            }

                        }else{
                            Toast.makeText(DetailMesinActivity.this, "terjadi error, mohon refresh", Toast.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onFailure(Call<ApiResponse> call, Throwable t) {
                        Log.d("TAG", "onFailure: " + t);

                    }
                });
            }else {
                Toast.makeText(DetailMesinActivity.this, "Mohon Lengkapi Data Diagnosa", Toast.LENGTH_SHORT).show();

            }
        }else{
            RequestUpdateMesin r = new RequestUpdateMesin(estselesai,idmesin,biaya,diagnosa,idDiagnosa,status,idteknisi);

            mApiInterface = ApiClient.getClient(this).create(ApiInterfaces.class);
            Call<ApiResponse> lm = mApiInterface.updatemesin(r);
            lm.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    if(response.isSuccessful()){
                        if (response.body().getCode().equals("200")){
                            switch(status) {
                                case "1" :
                                    Toast.makeText(DetailMesinActivity.this, "Berhasil Terima Mesin", Toast.LENGTH_SHORT).show();
                                    break;
                                case "4" :
                                    Toast.makeText(DetailMesinActivity.this, "Berhasil Mengabari Mesin Selesai", Toast.LENGTH_SHORT).show();
                                    break;


                            }
                            finish();
                        }else{
                            Log.d("TAG", "onResponse: " + response.body().getCode() + response.body().getMessage());
                        }

                    }else{
                        Toast.makeText(DetailMesinActivity.this, "terjadi error, mohon refresh", Toast.LENGTH_SHORT).show();
                    }


                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    Log.d("TAG", "onFailure: " + t);

                }
            });
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        new Handler().postDelayed(this::getDataMesin,2000);
    }
}