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
import android.widget.EditText
import androidx.lifecycle.AndroidViewModel
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.ToastUtils
import com.google.firebase.database.*
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.mieone.feedbackcollection.R
import com.mieone.feedbackcollection.application.MyApplication
import com.mieone.feedbackcollection.dialog.DownloadTask
import com.mieone.feedbackcollection.model.EmployeeFeedbackModel
import com.mieone.feedbackcollection.model.UpdateModel
import com.mieone.feedbackcollection.utils.ActivityManager
import com.mieone.feedbackcollection.utils.Constants
import com.mieone.feedbackcollection.utils.General
import com.yarolegovich.lovelydialog.LovelyStandardDialog
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class MainViewModel(application: Application) : AndroidViewModel(application) {

    internal var count = 0
    private var languageList = ArrayList<String>()
    lateinit var url: String
    var downloadTask: DownloadTask?=null
    val handler: Handler = Handler()

    var name: String? = null
    var manager: String? = null
    var vendor: String? = null
    var department: String? = null
    var doj: String? = null
    var employee_status: String? = null

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
//                            val scanned_id = et_employee_id.text.toString().replace("[-+.^:,]", "")
                            val scanned_id = et_employee_id.text.toString()
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

//        if (General.getEmployeeID().equals(scanned_id)){
//            General.redDialog(activity, "$scanned_id Already checked out for the day.", null)
//
//            return
//        }

        MyApplication.get()?.getmFirebaseFirestore()?.collection(Constants.EMPLOYEE_RECORDS)
                ?.document(scanned_id)?.get()?.addOnCompleteListener { task ->
            if (task.isSuccessful) {

                val model = EmployeeFeedbackModel()

                val document = task.result

                if (document!!.exists()) {

                    name = document.getString("name")
                    manager = document.getString("manager")
                    vendor = document.getString("vendor")
                    department = document.getString("department")
                    doj = document.getString("doj")
                    if (document.getString("employee_status") != null) {
                        employee_status = document.getString("employee_status")
                    }else{
                        employee_status = "Active"
                    }


                    model.name = name
                    model.manager = manager
                    model.vendor = vendor
                    model.department = department
                    model.doj = doj
                    model.check_in_time = System.currentTimeMillis()
                    model.isCheckedIn = true
                    model.employee_id = scanned_id
                    model.employee_status = employee_status

                } else {

                    name = ""
                    manager = ""
                    vendor = ""
                    department = ""
                    doj = ""
                    employee_status = ""
                    model.check_in_time = System.currentTimeMillis()
                    model.isCheckedIn = true
                    model.employee_id = scanned_id

                    General.redDialog(activity, "Not Authorized!! Please contact your manager.", null)
                    LogUtils.e(scanned_id)


                }
//                getLastCheckTime(scanned_id, activity, model)
                updateInDB(activity, scanned_id, model)

                LogUtils.e(scanned_id)

            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun updateInDB(activity: Activity, scanned_id: String, model: EmployeeFeedbackModel){

        val time = System.currentTimeMillis()

        try {
            val string1 = "16:59:00"
            val time1 = SimpleDateFormat("HH:mm:ss").parse(string1)
            val calendar1 = Calendar.getInstance()
            calendar1.time = time1

            val string2 = "07:01:00"
            val time2 = SimpleDateFormat("HH:mm:ss").parse(string2)
            val newDate =  Date(time2.time + 1 * 24 * 60 * 60 * 1000)
            val calendar2 = Calendar.getInstance()
            calendar2.time = newDate

            val time3 = System.currentTimeMillis()
            val date = Date(time3)
            val format = SimpleDateFormat("HH:mm:ss")
            val getDate = format.format(date)

            val d = SimpleDateFormat("HH:mm:ss").parse(getDate)
            val calendar3 = Calendar.getInstance()
            calendar3.time = d
            val x = calendar3.time
            if (x.after(calendar1.time) && x.before(calendar2.time)) {
                model.check_in_time = time
                model.isCheckedIn = true
                model.employee_id = scanned_id

//                MyApplication.get()?.getmFirebaseFirestore()?.collection(Constants.EMPLOYEE_FEEDBACK)
//                        ?.document()
//                        ?.set(model)
                name?.let { manager?.let { it1 -> vendor?.let { it2 -> department?.let { it3 -> doj?.let { it4 -> employee_status?.let { it5 -> ActivityManager.FEEDBACK(activity, it, it1, it2, it3, it4, scanned_id, it5) } } } } } }
            }else{
                General.redDialog(activity, "You have crossed the check in time for today.", null)
            }
        } catch (e: ParseException) {
            e.printStackTrace()
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