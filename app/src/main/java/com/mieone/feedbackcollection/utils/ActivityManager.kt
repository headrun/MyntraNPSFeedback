package com.mieone.feedbackcollection.utils

import android.content.Context
import android.content.Intent

import com.mieone.feedbackcollection.ui.FeedbackActivity
import com.mieone.feedbackcollection.ui.MainActivity

import spencerstudios.com.bungeelib.Bungee

object ActivityManager {

    fun HOME(context: Context) {

        context.startActivity(Intent(context, MainActivity::class.java))
    }

    fun FEEDBACK(context: Context, employee_id: String) {

        val intent = Intent(context, FeedbackActivity::class.java)
        intent.putExtra("employee_id", employee_id)
        context.startActivity(intent)
        Bungee.slideLeft(context)
    }

}
