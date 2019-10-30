package com.mieone.feedbackcollection.ui

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.NetworkUtils
import com.mieone.feedbackcollection.utils.General
import androidx.lifecycle.ViewModelProviders
import com.mieone.feedbackcollection.utils.Constants
import com.mieone.feedbackcollection.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import android.os.*
import com.blankj.utilcode.util.LogUtils
import com.bumptech.glide.Glide
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.mieone.feedbackcollection.R
import com.mieone.feedbackcollection.application.MyApplication
import com.yarolegovich.lovelydialog.LovelyStandardDialog

class MainActivity : AppCompatActivity() {

    private lateinit var verCode: String
    private lateinit var mainViewModel: MainViewModel
    private var dialog:LovelyStandardDialog?=null
    private var databaseReference: DatabaseReference? = null

    private fun initializeUpdateDialog(){
        dialog = LovelyStandardDialog(this)
                .setTopColorRes(R.color.green)
                .setIcon(R.drawable.ic_error)
                .setCancelable(true)
                .setTitle("Update Available!")
                .setMessage("NPS App just got better. Get the whole new experience of the app, just a click away.")
                .setPositiveButton("Update") {
                    dialog?.dismiss()
                    databaseReference?.let { it1 -> mainViewModel.requestPermission(it1, this) }
                }
    }

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)


        init()

        et_employee_id.requestFocus()

        try {
            val pInfo = this.packageManager.getPackageInfo(packageName, 0)
            val version = pInfo.versionName
            verCode = pInfo.versionCode.toString()
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        Glide.with(this).load(R.drawable.ic_barcode).into(img_barcode)

        mainViewModel.anonymousLogin(this)

//        MyApplication.get()?.getmFirebaseFirestore()?.collection(Constants.EMPLOYEE_FEEDBACK)
//                ?.whereEqualTo("employee_id", "asdfgh")
//                ?.get()
//                ?.addOnCompleteListener { task ->
//                    LogUtils.e(task.isSuccessful)
//                    LogUtils.e(task.result?.isEmpty)
//
//                    LogUtils.e("Size -> "+task.result?.size())
//
//                    for (document in task.result!!) {
//                        LogUtils.e(document.id)
//                        MyApplication.get()?.getmFirebaseFirestore()?.collection(Constants.EMPLOYEE_FEEDBACK)
//                                ?.document(document.id)?.delete()
//                    }
//                }


    }

    private fun init() {

        if (!NetworkUtils.isConnected()) {
            General.redDialog(this@MainActivity, "Oops no Internet Connection!", null)
        }

        General.lang = "hindi"

        mainViewModel.getLanguageList(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startLockTask()
        }

        General.hideKeyboard(this)

        initializeUpdateDialog()

        databaseReference = FirebaseDatabase.getInstance().getReference(Constants.APK_Path)

        //add text watcher in edittext
        mainViewModel.addTextWatcher(et_employee_id, this)


    }

    override fun onResume() {
        super.onResume()

        dialog?.let { mainViewModel.getUpdate(verCode, it) }
    }


    override fun onPause() {
        super.onPause()
        dialog?.dismiss()
    }

    override fun onBackPressed() {

    }

}
