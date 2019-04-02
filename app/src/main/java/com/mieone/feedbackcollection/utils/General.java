package com.mieone.feedbackcollection.utils;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.mieone.feedbackcollection.R;
import com.tapadoo.alerter.Alerter;

import io.paperdb.Paper;

public class General {

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void alertDialog(Activity activity, String title, String message){
        Alerter.create(activity)
                .setTitle(title)
                .setText(message)
                .setIcon(R.drawable.ic_tick)
                .setBackgroundColorRes(R.color.green)
                .setDuration(3000)
                .show();
    }

    public static void redDialog(Activity activity, String title, String message){
        Alerter.create(activity)
                .setTitle(title)
                .setText(message)
                .setIcon(R.drawable.ic_error)
                .setBackgroundColorRes(R.color.red)
                .setDuration(3000)
                .show();
    }

    public static void setLang(String lang){
        Paper.book().write("lang", lang);
    }

    public static String getLang(){
        return Paper.book().read("lang");
    }
}
