package com.mieone.feedbackcollection.viewmodel

import `in`.galaxyofandroid.spinerdialog.SpinnerDialog
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.lifecycle.AndroidViewModel
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.ToastUtils
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.mieone.feedbackcollection.R
import com.mieone.feedbackcollection.application.MyApplication
import com.mieone.feedbackcollection.dialog.UpdateDialog
import com.mieone.feedbackcollection.model.EmployeeFeedbackModel
import com.mieone.feedbackcollection.utils.ActivityManager
import com.mieone.feedbackcollection.utils.Constants
import com.mieone.feedbackcollection.utils.General
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class MainViewModel(application: Application) : AndroidViewModel(application) {

    internal var count = 0
    private var languageList = ArrayList<String>()

    fun getLanguageList(activity: Activity) {
        val languageDialog = SpinnerDialog(activity, languageList, "Select or Search Language", R.style.DialogAnimations_SmileWindow)

        languageDialog.bindOnSpinerListener { s, _ ->
            General.lang = s
            ToastUtils.showLong(s)
        }
        MyApplication.get()?.getmFirebaseFirestore()?.collection(Constants.FEEDBACK_QUESTION)?.get()?.addOnCompleteListener { task ->
            for (document in task.result!!) {
                languageList.add(document.id)
            }
            languageList.remove("english")
            languageDialog.showSpinerDialog()
        }
    }

    fun getUpdate(verCode: String, dialog: UpdateDialog, context: Activity) {

        MyApplication.get()?.getmFirebaseDataBase()?.getReference(Constants.APK_Path)?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (!dataSnapshot.hasChild(verCode)) {
                    dialog.show()
                    dialog.setUpdateBtn(context, context)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }

    fun addTextWatcher(et_employee_id: EditText, activity: Activity){

        et_employee_id.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }
            override fun afterTextChanged(s: Editable) {
                if (!NetworkUtils.isConnected()) {
                    General.redDialog(activity, "Oops no Internet Connection!", null)
                    return
                }
                Handler().postDelayed(
                        {
                            val scanned_id = et_employee_id.text.toString()
                            if (scanned_id.length >= 4) {
                                count++
                                if (count == 1) {

                                    getEmployeeDetails(scanned_id, activity, et_employee_id)

                                    //                                        getLastCheckTime(scanned_id);

                                    General.hideKeyboard(activity)

                                    et_employee_id.text.clear()
                                    et_employee_id.requestFocus()

                                    count = 0
                                }
                            }
                        }, 1000)

            }
        })
    }

    private fun getEmployeeDetails(scanned_id: String, activity: Activity, et_employee_id: EditText) {

        MyApplication.get()?.getmFirebaseFirestore()?.collection(Constants.EMPLOYEE_RECORDS)?.document(scanned_id)?.get()?.addOnCompleteListener { task ->
            if (task.isSuccessful) {

                val model = EmployeeFeedbackModel()

                val document = task.result

                LogUtils.e(document?.exists())

                if (document!!.exists()) {

                    val name = document.getString("name")
                    val manager = document.getString("manager")
                    val employee_code = document.getString("employee_code")
                    val vendor = document.getString("vendor")
                    val department = document.getString("department")
                    val doj = document.getString("doj")

                    model.name = name
                    model.manager = manager
                    model.vendor = vendor
                    model.department = department
                    model.doj = doj

                    getLastCheckTime(scanned_id, activity, et_employee_id, model)

                } else {

//                    val employeeData = HashMap<String, Any>()
//                    employeeData["name"] = " "
//                    employeeData["manager"] = " "
//                    employeeData["employee_code"] = " "
//                    employeeData["vendor"] = " "
//                    employeeData["department"] = " "
//                    employeeData["doj"] = " "

                    General.redDialog(activity, "Not Authorized!! Please contact your manager.", null)
                    getLastCheckTime(scanned_id, activity, et_employee_id, model)

//                    MyApplication.get()?.getmFirebaseFirestore()?.collection(Constants.EMPLOYEE_RECORDS)
//                            ?.document(scanned_id)?.set(model)?.addOnSuccessListener {
//                                getLastCheckTime(scanned_id, activity, et_employee_id, model)
//                            }

                }

            }
        }

    }

    private fun getLastCheckTime(scanned_id: String, activity: Activity, et_employee_id: EditText, model: EmployeeFeedbackModel) {

        if (!NetworkUtils.isConnected()) {
            General.redDialog(activity, "Oops no Internet Connection!", null)
            return
        }

        val time = System.currentTimeMillis()
        val date = Date(time)
        @SuppressLint("SimpleDateFormat") val format = SimpleDateFormat("dd-MM-yyyy")
        val getDate = format.format(date)

        MyApplication.get()?.getmFirebaseFirestore()?.collection(Constants.EMPLOYEE_FEEDBACK)
                ?.document(scanned_id)?.get()?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                if (document!!.exists()) {
                    val last_check_in = document.getLong("last_check_in")!!
                    val runningTime = System.currentTimeMillis() - last_check_in
                    val mins = TimeUnit.MILLISECONDS.toMinutes(runningTime)

                    if (mins > 15 * 60) {

                        updateCheckInTime(scanned_id, getDate, time, activity, et_employee_id, model)

                    } else {

                        getCheckInTime(scanned_id, last_check_in, activity, et_employee_id)
                    }


                } else {
                    LogUtils.e("No such document")
                    updateCheckInTime(scanned_id, getDate, time, activity, et_employee_id, model)
                }

                General.hideKeyboard(activity)
                et_employee_id.text.clear()
                et_employee_id.requestFocus()

            } else {
                LogUtils.e("get failed with ", task.exception)
                General.hideKeyboard(activity)
                et_employee_id.text.clear()
                et_employee_id.requestFocus()
            }
        }

    }

    private fun updateCheckInTime(scanned_id: String, date: String, time: Long,
                                  activity: Activity, et_employee_id: EditText, model: EmployeeFeedbackModel) {


        try {
            val date1 = Date(System.currentTimeMillis())
            @SuppressLint("SimpleDateFormat") val format = SimpleDateFormat("HH:mm:ss")
            val getTime = format.format(date1)
            val time1 = SimpleDateFormat("HH:mm:ss").parse(getTime)
            val calendar1 = Calendar.getInstance()
            calendar1.time = time1

            val someRandomTime = "13:00:00"
            val d = SimpleDateFormat("HH:mm:ss").parse(someRandomTime)
            val calendar3 = Calendar.getInstance()
            calendar3.time = d
            //            calendar3.add(Calendar.DATE, 1);

            val x = calendar3.time
            if (x.after(calendar1.time)) {
                General.alertDialog(activity, "Checked in Successfully.", null)
                model.check_in_time = time
                model.isCheckedIn = true
                model.employee_id = scanned_id
                model.check_in_date = date

                MyApplication.get()?.getmFirebaseFirestore()?.collection(Constants.EMPLOYEE_FEEDBACK)
                        ?.document(scanned_id)?.collection(Constants.EMPLOYEE_SESSION)?.document()
                        ?.set(model)?.addOnSuccessListener { updateLastCheckInTime(scanned_id, time, activity, et_employee_id) }
                        ?.addOnFailureListener {
                            General.hideKeyboard(activity)
                            et_employee_id.text.clear()
                            et_employee_id.requestFocus()
                        }
            } else {
                General.redDialog(activity, "You have crossed the check-in time for today.", null)
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        //        if (pm.equals(getTime)) {
        //
        //            General.redDialog(MainActivity.this, "You have crossed the check-in time for today.", null);
        //
        //        }else {
        //            General.alertDialog(MainActivity.this, "Checked in Successfully.", null);
        //
        //            model.setCheck_in_time(time);
        //            model.setCheckedIn(true);
        //            model.setet_employee_id(scanned_id);
        //            model.setCheck_in_date(date);
        //
        //            MyApplication.get().getmFirebaseFirestore().collection(Constants.EMPLOYEE_FEEDBACK)
        //                    .document(scanned_id).collection(Constants.EMPLOYEE_SESSION).document().set(model)
        //                    .addOnSuccessListener(new OnSuccessListener<Void>() {
        //                        @Override
        //                        public void onSuccess(Void aVoid) {
        //
        //                            updateLastCheckInTime(scanned_id, time);
        //
        //                        }
        //                    }).addOnFailureListener(new OnFailureListener() {
        //                @Override
        //                public void onFailure(@NonNull Exception e) {
        //                    General.hideKeyboard(MainActivity.this);
        //                    et_employee_id.getText().clear();
        //                    et_employee_id.requestFocus();
        //                }
        //            });
        //        }

    }

    private fun getCheckInTime(scanned_id: String, check_in_time: Long, activity: Activity, et_employee_id: EditText) {

        MyApplication.get()?.getmFirebaseFirestore()?.collection(Constants.EMPLOYEE_FEEDBACK)?.document(scanned_id)?.collection(Constants.EMPLOYEE_SESSION)?.whereEqualTo("check_in_time", check_in_time)?.get()?.addOnCompleteListener { task ->
            val snapshots = task.result

            val check_in_time = snapshots!!.documents[0].getLong("check_in_time")!!
            val runningTime = System.currentTimeMillis() - check_in_time

            val mins = TimeUnit.MILLISECONDS.toMinutes(runningTime)

            LogUtils.e("Minutes ->$mins")

            if (mins > 6 * 60) {

                if (snapshots.documents[0].getBoolean("checkedOut") == true) {

                    General.redDialog(activity, "Already checked out for the day.", null)

                } else {

                    ActivityManager.FEEDBACK(activity, scanned_id, snapshots.documents[0].id)

                }
            } else {

                General.redDialog(activity, "Already checked in.", null)
            }

            et_employee_id.text.clear()
            et_employee_id.requestFocus()
        }?.addOnFailureListener {
            General.hideKeyboard(activity)
            et_employee_id.text.clear()
            et_employee_id.requestFocus()
        }

    }

    private fun updateLastCheckInTime(scanned_id: String, time: Long, activity: Activity, et_employee_id: EditText) {

        val data = HashMap<String, Any>()
        data["last_check_in"] = time

        MyApplication.get()?.getmFirebaseFirestore()?.collection(Constants.EMPLOYEE_FEEDBACK)?.document(scanned_id)?.set(data)?.addOnSuccessListener { }?.addOnFailureListener {
            General.hideKeyboard(activity)
            et_employee_id.text.clear()
            et_employee_id.requestFocus()
        }
    }
}