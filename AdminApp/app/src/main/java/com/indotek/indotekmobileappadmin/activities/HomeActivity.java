package com.indotek.indotekmobileappadmin.activities;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.indotek.indotekmobileappadmin.R;
import com.indotek.indotekmobileappadmin.adapter.NotifAdapter;
import com.indotek.indotekmobileappadmin.backgroundservices.GetDataIntentService;
import com.indotek.indotekmobileappadmin.datamodel.DataNotif;
import com.indotek.indotekmobileappadmin.datamodel.NotifModel;
import com.indotek.indotekmobileappadmin.rest.ApiClient;
import com.indotek.indotekmobileappadmin.rest.ApiInterfaces;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeActivity extends AppCompatActivity {

    private TextView namaadmintv;
    private RecyclerView notifrv;
    private ApiInterfaces mApiInterface;
    private NotifAdapter notifAdapter;
    private LinearLayout llsplash;
    private Context context;
    private SharedPreferences sharedPref;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        llsplash = findViewById(R.id.llsplash);
        context = this;
        sharedPref = this.getSharedPreferences(getString(R.string.namasp), Context.MODE_PRIVATE);

        if (sharedPref.getString(getString(R.string.jwttoken),null) != null ){
            runHome();
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                llsplash.setVisibility(View.GONE);
            }, 2500);
        }else{
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                Intent i = new Intent(HomeActivity.this,LoginActivity.class);
                startActivity(i);
            }, 2500);

        }

    }

    private void runHome(){
        namaadmintv = findViewById(R.id.namaadmintv);
        notifrv = findViewById(R.id.notifrv);
        String namaadminnn = sharedPref.getString(getString(R.string.namauser),"Admin");
        namaadmintv.setText(namaadminnn);
        startService();
        checkHomeBerkala();
        getData();
    }


    private void getData(){
        mApiInterface = ApiClient.getClient(this).create(ApiInterfaces.class);

        Call<NotifModel> lm = mApiInterface.getnotif();

        lm.enqueue(new Callback<NotifModel>() {
            @Override
            public void onResponse(Call<NotifModel> call, Response<NotifModel> response) {
                if(response.isSuccessful()){
                    if (response.body().getCode().equals("200")){
                        List<DataNotif> data = response.body().dataNotifgetDataNotif();
                        notifrv.setLayoutManager(new LinearLayoutManager(context));
                        notifAdapter = new NotifAdapter(data,context);
                        notifrv.setAdapter(notifAdapter);
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Mohon update link api ", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<NotifModel> call, Throwable t) {


            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sharedPref.getString(getString(R.string.jwttoken),null) != null ){
            getData();
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                llsplash.setVisibility(View.GONE);
            }, 2500);
        }else{
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                Intent i = new Intent(HomeActivity.this,LoginActivity.class);
                startActivity(i);
            }, 2500);
        }

    }

    public void checkHomeBerkala() {
        new Handler().postDelayed(() -> {
            getData();
            checkHomeBerkala();
        }, 2500);
    }


    public void startService() {
        Intent serviceIntent = new Intent(this, GetDataIntentService.class);
        serviceIntent.putExtra("inputExtra", "Anda dapat menerima notifikasi Request Foto");
        ContextCompat.startForegroundService(this, serviceIntent);
    }
}