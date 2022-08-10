package com.indotek.mobileindotek.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.indotek.mobileindotek.R;
import com.indotek.mobileindotek.adapter.OrderanAdapter;
import com.indotek.mobileindotek.backgroundservices.GetDataIntentService;
import com.indotek.mobileindotek.datamodel.LoginModel;
import com.indotek.mobileindotek.datamodel.RequestCekNotif;
import com.indotek.mobileindotek.datamodel.ResponseCekNotif;
import com.indotek.mobileindotek.rest.ApiClient;
import com.indotek.mobileindotek.rest.ApiInterfaces;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    private Context context;
    private SharedPreferences sharedPref;
    private LinearLayout llsplash;
    private TextView namatek,tvorderan,tvproses,tvtanggal,connectindic;
    private ImageView ivsettingprofile,ivntwkindic;
    private RecyclerView rvutama;
    private OrderanAdapter orderanAdapter;
    private ApiInterfaces mApiInterface;
    private String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);
        llsplash = findViewById(R.id.llsplash);
        rvutama = findViewById(R.id.rvutama);
        tvorderan = findViewById(R.id.tvorderan);
        tvproses = findViewById(R.id.tvproses);
        tvtanggal = findViewById(R.id.tvtanggal);
        connectindic = findViewById(R.id.connectedindic);
        ivntwkindic = findViewById(R.id.ivnetwkindic);

        context = this;
        sharedPref = this.getSharedPreferences(getString(R.string.namasp), Context.MODE_PRIVATE);
        Date date = Calendar.getInstance().getTime();
        String tanggalToday = new SimpleDateFormat("d MMM yyyy").format(date);
        tvtanggal.setText(tanggalToday);

        if (sharedPref.getString(getString(R.string.jwttoken),null) != null ){
            runHome();
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                llsplash.setVisibility(View.GONE);
                status = "0";
                getDataOrderan(status);
            }, 2500);
        }else{
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                Intent i = new Intent(HomeActivity.this,LoginActivity.class);
                startActivity(i);
            }, 2500);

        }

        tvorderan.setOnClickListener(v-> {
            status = "0";
            getDataOrderan(status);
            rvutama.removeAllViewsInLayout();
            tvorderan.setBackgroundColor(context.getResources().getColor(R.color.maroon));
            tvorderan.setTextColor(context.getResources().getColor(R.color.white));
            tvproses.setBackgroundColor(context.getResources().getColor(R.color.darkgray));
            tvproses.setTextColor(context.getResources().getColor(R.color.fontgray));
        });
        tvproses.setOnClickListener(v-> {
            status = "99";
            getDataOrderan(status);
            rvutama.removeAllViewsInLayout();
            tvproses.setBackgroundColor(context.getResources().getColor(R.color.maroon));
            tvproses.setTextColor(context.getResources().getColor(R.color.white));
            tvorderan.setBackgroundColor(context.getResources().getColor(R.color.darkgray));
            tvorderan.setTextColor(context.getResources().getColor(R.color.fontgray));
        });
        startService();
    }

    private void runHome(){
        namatek = findViewById(R.id.tvnamateknisi);
        ivsettingprofile = findViewById(R.id.ivsettingprofile);
        ivsettingprofile.setOnClickListener(v-> startActivity(new Intent(HomeActivity.this,ProfileActivity.class)));
        String sNamatek = sharedPref.getString(getString(R.string.namauser),"Admin");
        namatek.setText(sNamatek);

        int handler_time = 5000; //5 seconds in milliseconds
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {

            @Override
            public void run() {

                if(isOnline(context)){
                getDataOrderan(status);
                    connectindic.setText("Terhubung");
                    ivntwkindic.clearColorFilter();
                }else{
                    connectindic.setText("Terputus");
                    ivntwkindic.setColorFilter(ContextCompat.getColor(context, R.color.graybg), android.graphics.PorterDuff.Mode.SRC_IN);
                }
                handler.postDelayed(this, handler_time);

            }

        };

        handler.post(runnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sharedPref.getString(getString(R.string.jwttoken),null) != null ){
            getDataOrderan(status);
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

    private void getDataOrderan(String status){
        String sIdtek = sharedPref.getString(getString(R.string.iduser),"");
        LinearLayout llloadingrv = findViewById(R.id.llloadingrv);
        TextView tvloadingrv = findViewById(R.id.tvloadingrv);
        llloadingrv.setVisibility(View.VISIBLE);
        tvloadingrv.setText("Memuat...");
        mApiInterface = ApiClient.getClient(this).create(ApiInterfaces.class);
        Call<ResponseCekNotif> lm = mApiInterface.getnotif(sIdtek,status);
        lm.enqueue(new Callback<ResponseCekNotif>() {
            @Override
            public void onResponse(Call<ResponseCekNotif> call, Response<ResponseCekNotif> response) {
                if(response.isSuccessful()){
                    if (response.body().getCode().equals("200")){
                       rvutama.setLayoutManager(new LinearLayoutManager(context));
                       orderanAdapter = new OrderanAdapter(response.body().getObject(),context);
                       rvutama.setAdapter(orderanAdapter);
                        llloadingrv.setVisibility(View.GONE);

                    }
                    else {
                        tvloadingrv.setText("terjadi error, coba muat ulang");
                        llloadingrv.setOnClickListener(v->getDataOrderan(status));
                    }

                }else{
                    Toast.makeText(context, "mohon update link API", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<ResponseCekNotif> call, Throwable t) {
                tvloadingrv.setText("Belum Ada orderan baru");


            }
        });
    }

    public void startService() {
        Intent serviceIntent = new Intent(this, GetDataIntentService.class);
        serviceIntent.putExtra("inputExtra", "Anda dapat menerima notifikasi Orderan Baru");
        ContextCompat.startForegroundService(this, serviceIntent);
    }
    public static boolean isOnline(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnected());
    }

}