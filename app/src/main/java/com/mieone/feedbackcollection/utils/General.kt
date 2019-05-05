package com.mieone.feedbackcollection.utils

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager

import com.mieone.feedbackcollection.R
import com.tapadoo.alerter.Alerter

import io.paperdb.Paper

object General {

    var lang: String
        get() = Paper.book().read("lang")
        set(lang) {
            Paper.book().write("lang", lang)
        }

    fun hideKeyboard(activity: Activity) {
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun alertDialog(activity: Activity, title: String, message: String?) {
        Alerter.create(activity)
                .setTitle(title)
                .setText(message)
                .setIcon(R.drawable.ic_tick)
                .setBackgroundColorRes(R.color.green)
                .setDuration(3000)
                .show()
    }

    fun redDialog(activity: Activity, title: String, message: String?) {
        Alerter.create(activity)
                .setTitle(title)
                .setText(message)
                .setIcon(R.drawable.ic_error)
                .setBackgroundColorRes(R.color.red)
                .setDuration(3000)
                .show()
    }
}
