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

    //        try {
//            val date1 = Date(System.currentTimeMillis())
//            @SuppressLint("SimpleDateFormat") val format = SimpleDateFormat("HH:mm:ss")
//            val getTime = format.format(date1)
//            val time1 = SimpleDateFormat("HH:mm:ss").parse(getTime)
//            val calendar1 = Calendar.getInstance()
//            calendar1.time = time1
//
//            val someRandomTime = "13:00:00"
//            val d = SimpleDateFormat("HH:mm:ss").parse(someRandomTime)
//            val calendar3 = Calendar.getInstance()
//            calendar3.time = d
//            //            calendar3.add(Calendar.DATE, 1);
//
//            val x = calendar3.time
//            if (x.after(calendar1.time)) {
//                General.alertDialog(activity, "Checked in Successfully.", null)
//                model.check_in_time = time
//                model.isCheckedIn = true
//                model.employee_id = scanned_id
//                model.check_in_date = date
//
//                MyApplication.get()?.getmFirebaseFirestore()?.collection(Constants.EMPLOYEE_FEEDBACK)
//                        ?.document(scanned_id)?.collection(Constants.EMPLOYEE_SESSION)?.document()
//                        ?.set(model)?.addOnSuccessListener { updateLastCheckInTime(scanned_id, time, activity, et_employee_id) }
//                        ?.addOnFailureListener {
//                            General.hideKeyboard(activity)
//                            et_employee_id.text.clear()
//                            et_employee_id.requestFocus()
//                        }
//            } else {
//                General.redDialog(activity, "You have crossed the check-in time for today.", null)
//            }
//        } catch (e: ParseException) {
//            e.printStackTrace()
//        }
}
