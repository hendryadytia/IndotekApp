package com.indotek.mobileindotek.backgroundservices;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.indotek.mobileindotek.R;
import com.indotek.mobileindotek.activities.HomeActivity;
import com.indotek.mobileindotek.activities.LoginActivity;
import com.indotek.mobileindotek.datamodel.DataNotif;
import com.indotek.mobileindotek.datamodel.NotifModel;
import com.indotek.mobileindotek.rest.ApiClient;
import com.indotek.mobileindotek.rest.ApiInterfaces;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetDataIntentService extends Service {
    public static final String CHANNEL_ID = "ForegroundServiceChannel";
    public static final String CHANNEL_ID_REQ = "ReuestFotoMesin";
    HashMap<String,DataNotif> hashMapDatanotif = new HashMap<>();
    Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String input = intent.getStringExtra("inputExtra");
        createNotificationChannel();
        Intent notificationIntent = new Intent(this, LoginActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Indotek Teknisi App Notification Service")
                .setContentText(input)
                .setSmallIcon(R.drawable.logo_indotek)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1, notification);

        int handler_time = 5000; //5 seconds in milliseconds
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                getData();
                handler.postDelayed(this, handler_time);

            }

        };

        handler.post(runnable);


        return START_NOT_STICKY;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_LOW
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);

            NotificationChannel serviceChannel2 = new NotificationChannel(
                    CHANNEL_ID_REQ,
                    "Orderan Baru",
                    NotificationManager.IMPORTANCE_HIGH
            );

            manager.createNotificationChannel(serviceChannel2);
        }
    }
//
    private void getData(){

        ApiInterfaces mApiInterface = ApiClient.getClient(this).create(ApiInterfaces.class);
        SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.namasp), Context.MODE_PRIVATE);
        String id = sharedPref.getString(getString(R.string.iduser),"");

        Call<NotifModel> lm = mApiInterface.getnotifbackground(id,"98");

        lm.enqueue(new Callback<NotifModel>() {
            @Override
            public void onResponse(Call<NotifModel> call, Response<NotifModel> response) {
                if(response.isSuccessful()){
                    if (response.body().getCode().equals("200")){
                        List<DataNotif> data = response.body().getData();
                        Intent notificationIntent = new Intent(context, HomeActivity.class);
                        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                                0, notificationIntent, 0);
                             NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

                            for (DataNotif d : data) {
                                if(!(hashMapDatanotif.containsKey(d.getId()))){
                                    hashMapDatanotif.put(d.getId(),d);
                                    if(d.getStatus().equals("0")){
                                        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID_REQ)
                                                .setSmallIcon(R.drawable.logo_indotek)
                                                .setContentTitle("Orderan Baru")
                                                .setContentText("Jenis Mesin: " + d.getJenisMesin() )
                                                .setOnlyAlertOnce(true)
                                                .setContentIntent(pendingIntent)
                                                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALL))
                                                .setPriority(NotificationCompat.PRIORITY_HIGH);


                                        notificationManager.notify(Integer.parseInt(new SimpleDateFormat("ddHHmmss").format(new Date())), builder.build());
                                    }else if(d.getStatus().equals("3")){
                                        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID_REQ)
                                                .setSmallIcon(R.drawable.logo_indotek)
                                                .setContentTitle("Diagnosa Di ACC")
                                                .setContentText("Jenis Mesin: " + d.getJenisMesin() )
                                                .setOnlyAlertOnce(true)
                                                .setContentIntent(pendingIntent)
                                                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALL))
                                                .setPriority(NotificationCompat.PRIORITY_HIGH);

                                        notificationManager.notify(Integer.parseInt(new SimpleDateFormat("ddHHmmss").format(new Date())), builder.build());

                                    }
                                }




                            }



                    }
                }


            }

            @Override
            public void onFailure(Call<NotifModel> call, Throwable t) {


            }
        });
    }

}

