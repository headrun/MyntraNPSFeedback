package com.mieone.feedbackcollection.utils;

import android.content.Context;
import android.content.Intent;

import com.mieone.feedbackcollection.FeedbackActivity;
import com.mieone.feedbackcollection.MainActivity;

import spencerstudios.com.bungeelib.Bungee;

public class ActivityManager {

    public static void HOME(Context context){

        context.startActivity(new Intent(context, MainActivity.class));
    }

    public static void FEEDBACK(Context context, String employee_id, String document_id){

        Intent intent = new Intent(context, FeedbackActivity.class);
        intent.putExtra("employee_id", employee_id);
        intent.putExtra("document_id", document_id);
        context.startActivity(intent);
        Bungee.slideLeft(context);
    }

}
