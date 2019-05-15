package com.mieone.feedbackcollection.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.widget.Toast;
import androidx.core.app.NotificationCompat;

import com.mieone.feedbackcollection.ui.MainActivity;

import static androidx.core.app.NotificationCompat.PRIORITY_MAX;

public class BackgroundService extends Service {
    boolean isStarted;


    @Override
    public void onCreate() {
        super.onCreate();
        isStarted = true;
        startServiceOreoCondition();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (isStarted){
            Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
            getBaseContext().startActivity(new Intent(getBaseContext(), MainActivity.class));
            isStarted = false;
        }
        startServiceOreoCondition();
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
        Toast.makeText(this, "Service Destroy", Toast.LENGTH_LONG).show();
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    private void startServiceOreoCondition(){
        if (Build.VERSION.SDK_INT >= 26) {


            String CHANNEL_ID = "my_service";
            String CHANNEL_NAME = "My Background Service";

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME, NotificationManager.IMPORTANCE_NONE);
            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setCategory(Notification.CATEGORY_SERVICE).setPriority(PRIORITY_MAX).build();

            startForeground(101, notification);
        }
    }
}
