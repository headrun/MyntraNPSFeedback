package com.mieone.feedbackcollection.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.mieone.feedbackcollection.service.BackgroundService;

public class BootCompletedIntentReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            Intent pushIntent = new Intent(context, BackgroundService.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(pushIntent);
            }else {
                context.startService(pushIntent);
            }
        }
    }
}
