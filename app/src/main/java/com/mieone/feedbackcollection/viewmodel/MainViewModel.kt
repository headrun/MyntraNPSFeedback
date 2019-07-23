package com.mieone.feedbackcollection.viewmodel

import `in`.galaxyofandroid.spinerdialog.SpinnerDialog
import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.os.Build
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.ToastUtils
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.*
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.mieone.feedbackcollection.R
import com.mieone.feedbackcollection.application.MyApplication
import com.mieone.feedbackcollection.dialog.DownloadFile
import com.mieone.feedbackcollection.dialog.DownloadTask
import com.mieone.feedbackcollection.dialog.UpdateDialog
import com.mieone.feedbackcollection.model.EmployeeFeedbackModel
import com.mieone.feedbackcollection.model.UpdateModel
import com.mieone.feedbackcollection.ui.MainActivity
import com.mieone.feedbackcollection.utils.ActivityManager
import com.mieone.feedbackcollection.utils.Constants
import com.mieone.feedbackcollection.utils.General
import com.yarolegovich.lovelydialog.LovelyInfoDialog
import com.yarolegovich.lovelydialog.LovelyStandardDialog
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class MainViewModel(application: Application) : AndroidViewModel(application) {

    internal var count = 0
    private var languageList = ArrayList<String>()
    var isExistence = true
    lateinit var url: String
    var downloadTask: DownloadTask?=null
    val handler: Handler = Handler()

    fun getLanguageList(activity: Activity) {
        val languageDialog = SpinnerDialog(activity, languageList, "Select or Search Language", R.style.DialogAnimations_SmileWindow)

        languageDialog.bindOnSpinerListener { s, _ ->
            General.lang = s
            ToastUtils.showLong(s)
        }
        MyApplication.get()?.getmFirebaseFirestore()?.collection(Constants.FEEDBACK_QUESTION)?.get()?.addOnCompleteListener { task ->
            languageList.clear()
            for (document in task.result!!) {
                languageList.add(document.id)
            }
            languageList.remove("english")
            languageDialog.showSpinerDialog()
        }
    }

    fun getUpdate(verCode: String, dialog: LovelyStandardDialog) {

        MyApplication.get()?.getmFirebaseDataBase()?.getReference(Constants.APK_Path)?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (!dataSnapshot.hasChild(verCode)) {
                    dialog.show()
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
                handler.removeCallbacksAndMessages(null)
            }
            override fun afterTextChanged(s: Editable) {
                if (!NetworkUtils.isConnected()) {
                    General.redDialog(activity, "Oops no Internet Connection!", null)
                    return
                }

                handler.postDelayed(
                    {
                            val scanned_id = et_employee_id.text.toString().replace("[-+.^:,]", "")
                            if (scanned_id.length in 4..12) {
                                count++
                                if (count == 1) {

                                    ToastUtils.showLong(scanned_id)

                                    getEmployeeDetails(scanned_id, activity)
                                    General.hideKeyboard(activity)

                                    et_employee_id.setText("")
                                    et_employee_id.requestFocus()

                                    count = 0
                                }
                            }else{
                                et_employee_id.setText("")
                                et_employee_id.requestFocus()
                            }
//                            }
                    }, 1000)

            }
        })
    }

    fun getEmployeeDetails(scanned_id: String, activity: Activity) {

        MyApplication.get()?.getmFirebaseFirestore()?.collection(Constants.EMPLOYEE_RECORDS)
                ?.document(scanned_id)?.get()?.addOnCompleteListener { task ->
            if (task.isSuccessful) {

                val model = EmployeeFeedbackModel()

                val document = task.result

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
                    model.check_in_time = System.currentTimeMillis()
                    model.isCheckedIn = true
                    model.employee_id = scanned_id

                    getLastCheckTime(scanned_id, activity, model)

                } else {

                    model.check_in_time = System.currentTimeMillis()
                    model.isCheckedIn = true
                    model.employee_id = scanned_id

                    General.redDialog(activity, "Not Authorized!! Please contact your manager.", null)
                    getLastCheckTime(scanned_id, activity, model)
                    LogUtils.e(scanned_id)

                }

                LogUtils.e(scanned_id)

            }
        }
    }

    private fun getLastCheckTime(scanned_id: String, activity: Activity, model: EmployeeFeedbackModel) {

        if (!NetworkUtils.isConnected()) {
            General.redDialog(activity, "Oops no Internet Connection!", null)
            return
        }

        val time = System.currentTimeMillis()
        val c = Calendar.getInstance()
        c.add(Calendar.HOUR, -16)

        LogUtils.e("TIME ->"+c.timeInMillis)

        MyApplication.get()?.getmFirebaseFirestore()?.collection(Constants.EMPLOYEE_FEEDBACK)
                ?.whereEqualTo("employee_id", scanned_id)
                ?.whereGreaterThan("check_in_time", c.timeInMillis)
                ?.get()
                ?.addOnCompleteListener { task ->
                    LogUtils.e(task.isSuccessful)
                    LogUtils.e(task.result?.isEmpty)

                    if (task.result?.isEmpty!!){
                        updateCheckInTime(scanned_id, time, activity, model)
                    } else{
                        General.redDialog(activity, "$scanned_id Already checked out for the day.", null)
                    }
                }

        //                                val runningTime = System.currentTimeMillis() - last_check_in
//                                val mins = TimeUnit.MILLISECONDS.toMinutes(runningTime)

    }

    @SuppressLint("SimpleDateFormat")
    private fun updateCheckInTime(scanned_id: String, time: Long,
                                  activity: Activity, model: EmployeeFeedbackModel) {

        try {
            val string1 = "16:59:00"
            val time1 = SimpleDateFormat("HH:mm:ss").parse(string1)
            val calendar1 = Calendar.getInstance()
            calendar1.time = time1

            val string2 = "7:01:00"
            val time2 = SimpleDateFormat("HH:mm:ss").parse(string2)
            val calendar2 = Calendar.getInstance()
            calendar2.time = time2

            val time3 = System.currentTimeMillis()
            val date = Date(time3)
            val format = SimpleDateFormat("HH:mm:ss")
            val getDate = format.format(date)

            val d = SimpleDateFormat("HH:mm:ss").parse(getDate)
            val calendar3 = Calendar.getInstance()
            calendar3.time = d

            val x = calendar3.time
            if (x.after(calendar1.time) && x.before(calendar2.time)) {
                LogUtils.e("true")
                General.alertDialog(activity, "Checked in Successfully with employee id $scanned_id", null)
                model.check_in_time = time
                model.isCheckedIn = true
                model.employee_id = scanned_id

                MyApplication.get()?.getmFirebaseFirestore()?.collection(Constants.EMPLOYEE_FEEDBACK)
                        ?.document()
                        ?.set(model)?.addOnSuccessListener {
                            val c = Calendar.getInstance()
                            c.add(Calendar.HOUR, -16)

                            MyApplication.get()?.getmFirebaseFirestore()?.collection(Constants.EMPLOYEE_FEEDBACK)
                                    ?.whereEqualTo("employee_id", scanned_id)
                                    ?.whereGreaterThan("check_in_time", c.timeInMillis)
                                    ?.get()
                                    ?.addOnCompleteListener { task ->

                                        for (document in task.result!!){
                                            LogUtils.e(document.id+" "+document.data)
                                            val afterTime = document.getLong("check_in_time")
                                            afterTime?.let { getCheckInTime(document.id, it, activity, scanned_id) }
                                        }
                                    }
                        }

//                getCheckInTime(scanned_id, time, activity, )
            }else{
                LogUtils.e("false")
                General.redDialog(activity, "You have crossed the check in time for today.", null)
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }


    }

    private fun getCheckInTime(document_id: String, check_in_time: Long, activity: Activity, scanned_id: String) {

        MyApplication.get()?.getmFirebaseFirestore()?.collection(Constants.EMPLOYEE_FEEDBACK)
                ?.document(document_id)?.get()?.addOnCompleteListener { task ->
                    val snapshots = task.result

                    val runningTime = System.currentTimeMillis() - check_in_time

                    val mins = TimeUnit.MILLISECONDS.toMinutes(runningTime)

                    LogUtils.e("Minutes ->$mins")

                    LogUtils.e(mins >  6 * 60)

                    if (snapshots?.getBoolean("checkedOut") == true) {

                        General.redDialog(activity, "$scanned_id Already checked out for the day.", null)

                    } else {

                        ActivityManager.FEEDBACK(activity, document_id)

                    }

//            if (mins >  6 * 60) {
//
//                    if (snapshots?.getBoolean("checkedOut") == true) {
//
//                        General.redDialog(activity, "$scanned_id Already checked out for the day.", null)
//
//                    } else {
//
//                        ActivityManager.FEEDBACK(activity, document_id)
//
//                    }
//                }
//            else {
//
//                    General.redDialog(activity, "$scanned_id Already checked in.", null)
//                }

        }?.addOnFailureListener {
            General.hideKeyboard(activity)
        }

    }

    fun anonymousLogin(activity: Activity){
        MyApplication.get()?.getmAuth()?.signInAnonymously()
                ?.addOnCompleteListener(activity) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        LogUtils.e( "signInAnonymously:success")
                    } else {
                        // If sign in fails, display a message to the user.
                        LogUtils.e("signInAnonymously:failure", task.exception)
                    }
                }
    }

    fun requestPermission(databaseReference: DatabaseReference, activity: Activity){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.stopLockTask()
        }

        Dexter.withActivity(activity)
                .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                        if (report.areAllPermissionsGranted()) {
                            LogUtils.e("Clicking...")
                            getAPKUrl(databaseReference,activity)
                        } else {
                        }

                    }

                    override fun onPermissionRationaleShouldBeShown(permissions: List<PermissionRequest>, token: PermissionToken) {
                        token.continuePermissionRequest()
                    }
                }).check()
    }

    private fun getAPKUrl(databaseReference: DatabaseReference, activity: Activity) {

        downloadTask = DownloadTask(activity)

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                for (dataSnapshot in snapshot.children) {

                    val updateModel = dataSnapshot.getValue(UpdateModel::class.java)
                    updateModel!!.url

                    url = updateModel.url.toString()

                    LogUtils.e(url)

                }

                LogUtils.e(url)
                downloadTask?.execute(url)


            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }
}