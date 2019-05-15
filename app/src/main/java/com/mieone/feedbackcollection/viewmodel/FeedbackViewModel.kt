package com.mieone.feedbackcollection.viewmodel

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.AndroidViewModel
import com.blankj.utilcode.util.ToastUtils
import com.mieone.feedbackcollection.R
import com.mieone.feedbackcollection.application.MyApplication
import com.mieone.feedbackcollection.utils.Constants
import com.mieone.feedbackcollection.utils.General
import kotlinx.android.synthetic.main.activity_feedback.*
import spencerstudios.com.bungeelib.Bungee
import java.text.SimpleDateFormat
import java.util.*

class FeedbackViewModel(application: Application) : AndroidViewModel(application) {

    var superior_feedback = " "
    var employee_id: String? = null

    fun displayView(position: Int, img_good: ImageView, img_avg: ImageView, img_poor: ImageView, activity: Activity) {

        when (position) {
            0 -> {
                superior_feedback = "Good"
                img_good.setImageResource(R.drawable.ic_good)
                img_avg.setImageResource(R.drawable.ic_uncheck_avg)
                img_poor.setImageResource(R.drawable.ic_uncheck_poor)
                getFeedbackSubmitted(activity)
            }

            1 -> {
                superior_feedback = "Average"
                img_avg.setImageResource(R.drawable.ic_average)
                img_good.setImageResource(R.drawable.ic_uncheck_good)
                img_poor.setImageResource(R.drawable.ic_uncheck_poor)
                getFeedbackSubmitted(activity)
            }

            2 -> {
                superior_feedback = "Poor"
                img_poor.setImageResource(R.drawable.ic_poor)
                img_good.setImageResource(R.drawable.ic_uncheck_good)
                img_avg.setImageResource(R.drawable.ic_uncheck_avg)
                getFeedbackSubmitted(activity)
            }
        }

    }

    private fun getFeedbackSubmitted(activity: Activity) {

        val time = System.currentTimeMillis()

        val date = Date(time)
        @SuppressLint("SimpleDateFormat") val format = SimpleDateFormat("dd-MM-yyyy")
        val getDate = format.format(date)

        MyApplication.get()?.getmFirebaseFirestore()?.collection(Constants.EMPLOYEE_FEEDBACK)
                ?.document(employee_id!!)
                ?.update(
                        "check_out_time", time,
                        "checkedOut", true,
                        "feedback_time", time,
                        "superior_experience", superior_feedback
                )
        ToastUtils.showLong("Thanks for giving feedback")
        activity.finish()
        Bungee.slideRight(activity)
    }

    fun getLanguage(multi_language_txt: TextView) {

        MyApplication.get()?.getmFirebaseFirestore()?.collection(Constants.FEEDBACK_QUESTION)?.document(General.lang)
                ?.get()?.addOnCompleteListener { task ->
                    val document = task.result

                    val question = document!!.getString("question")

                    multi_language_txt.text = question
                }
    }
}