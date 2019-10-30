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

    fun FEEDBACK(context: Context, name: String, manager: String, vendor: String, department: String, doj: String, scanned_id: String, employee_status: String) {

        val intent = Intent(context, FeedbackActivity::class.java)
        intent.putExtra("name", name)
        intent.putExtra("manager", manager)
        intent.putExtra("vendor", vendor)
        intent.putExtra("department", department)
        intent.putExtra("doj", doj)
        intent.putExtra("scanned_id", scanned_id)
        intent.putExtra("employee_status",employee_status)
        context.startActivity(intent)
        Bungee.slideLeft(context)
    }

}
