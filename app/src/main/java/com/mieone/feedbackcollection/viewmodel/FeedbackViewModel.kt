package com.mieone.feedbackcollection.viewmodel

import android.app.Activity
import android.app.Application
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.AndroidViewModel
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.mieone.feedbackcollection.R
import com.mieone.feedbackcollection.application.MyApplication
import com.mieone.feedbackcollection.model.EmployeeFeedbackModel
import com.mieone.feedbackcollection.utils.Constants
import com.mieone.feedbackcollection.utils.General
import spencerstudios.com.bungeelib.Bungee
import java.util.*

class FeedbackViewModel(application: Application) : AndroidViewModel(application) {

    var superior_feedback = " "
    var scanned_id: String? = null
    var name: String? = null
    var manager: String? = null
    var vendor: String? = null
    var department: String? = null
    var doj: String? = null
    var employee_status: String? = null

    fun displayView(position: Int, img_good: ImageView, img_avg: ImageView, img_poor: ImageView, activity: Activity) {

        when (position) {
            0 -> {
                superior_feedback = "Good"
                img_good.setImageResource(R.drawable.ic_good)
                img_avg.setImageResource(R.drawable.ic_uncheck_avg)
                img_poor.setImageResource(R.drawable.ic_uncheck_poor)
                updateInDB(activity)
            }

            1 -> {
                superior_feedback = "Average"
                img_avg.setImageResource(R.drawable.ic_average)
                img_good.setImageResource(R.drawable.ic_uncheck_good)
                img_poor.setImageResource(R.drawable.ic_uncheck_poor)
                updateInDB(activity)
            }

            2 -> {
                superior_feedback = "Poor"
                img_poor.setImageResource(R.drawable.ic_poor)
                img_good.setImageResource(R.drawable.ic_uncheck_good)
                img_avg.setImageResource(R.drawable.ic_uncheck_avg)
                updateInDB(activity)
            }
        }

    }

    private fun updateInDB(activity: Activity){

        val model = EmployeeFeedbackModel()
        val time = System.currentTimeMillis()

        model.name = name
        model.manager = manager
        model.vendor = vendor
        model.department = department
        model.doj = doj
        model.check_in_time = time
        model.isCheckedIn = true
        model.employee_id = scanned_id
        model.check_out_time =  time
        model.isCheckedOut = true
        model.feedback_time = time
        model.superior_experience = superior_feedback
        model.employee_status = employee_status

//        scanned_id?.let { General.storeEmployeeID(it) }

        MyApplication.get()?.getmFirebaseFirestore()?.collection(Constants.EMPLOYEE_FEEDBACK)
                ?.document()
                ?.set(model)

        ToastUtils.showLong("Thanks for giving feedback")
        activity.finish()
        Bungee.slideRight(activity)



//        scanned_id?.let { General.employeeList.add(it) }


//        val c = Calendar.getInstance()
//        c.add(Calendar.SECOND, -8)
//
//        MyApplication.get()?.getmFirebaseFirestore()?.collection(Constants.EMPLOYEE_FEEDBACK)
//                ?.whereEqualTo("employee_id", scanned_id)
//                ?.whereGreaterThan("check_in_time", c.timeInMillis)
//                ?.get()
//                ?.addOnCompleteListener { task ->
//
//                    for (document in task.result!!){
//                        LogUtils.e(document.id+" "+document.data)
//                        val afterTime = document.getLong("check_in_time")
////                        afterTime?.let { getCheckInTime(document.id, it, activity, scanned_id) }
//                        getFeedbackSubmitted(activity, document.id)
//                    }
//                }
    }

    private fun getFeedbackSubmitted(activity: Activity, document_id: String) {

        val time = System.currentTimeMillis()

        val date = Date(time)

        MyApplication.get()?.getmFirebaseFirestore()?.collection(Constants.EMPLOYEE_FEEDBACK)
                ?.document(document_id)
                ?.update(
                        "check_out_time", time,
                        "checkedOut", true,
                        "feedback_time", time,
                        "superior_experience", superior_feedback
                )
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