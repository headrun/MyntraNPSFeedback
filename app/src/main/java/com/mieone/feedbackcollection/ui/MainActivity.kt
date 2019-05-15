package com.mieone.feedbackcollection.ui

import android.content.pm.PackageManager
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.NetworkUtils
import com.mieone.feedbackcollection.utils.General
import androidx.lifecycle.ViewModelProviders
import com.mieone.feedbackcollection.utils.Constants
import com.mieone.feedbackcollection.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import android.os.*
import android.widget.Toast
import com.blankj.utilcode.util.LogUtils
import com.bumptech.glide.Glide
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.mieone.feedbackcollection.R
import com.mieone.feedbackcollection.application.MyApplication
import com.yarolegovich.lovelydialog.LovelyStandardDialog

class MainActivity : AppCompatActivity() {

//    private var dialog: UpdateDialog? = null
    private lateinit var verCode: String
    private lateinit var mainViewModel: MainViewModel
    private var Barcode = ""
    var count = 0
    private var dialog:LovelyStandardDialog?=null
    private var databaseReference: DatabaseReference? = null

    fun initializeUpdateDialog(){
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

//        dialog = UpdateDialog(this)

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
//                ?.whereEqualTo("employee_id", "56014417026")
//                ?.get()
//                ?.addOnCompleteListener { task ->
//                    LogUtils.e(task.isSuccessful)
//                    LogUtils.e(task.result?.isEmpty)
//
//                    LogUtils.e("Size -> "+task.result?.size())
//
//                    for (document in task.result!!) {
//                        LogUtils.e(document.id)
//                        val emp_id = document.getString("employee_id")
////                        val time = document.getLong("check_in_time")
////                        LogUtils.e(" Emp_id => $emp_id")
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

        et_employee_id.setOnKeyListener { v, keyCode, event ->

            if ((event.action == KeyEvent.ACTION_DOWN) &&
                    (keyCode == KeyEvent.KEYCODE_ENTER)) {
                // Perform action on key press
                Toast.makeText(this, et_employee_id.text.toString(), Toast.LENGTH_SHORT).show();
                return@setOnKeyListener true
            }

            return@setOnKeyListener false
        }
    }

//    override fun dispatchKeyEvent(e: KeyEvent) : Boolean{
//
//        if(e.action == KeyEvent.ACTION_DOWN
//                && e.keyCode != KeyEvent.KEYCODE_ENTER){ //Not Adding ENTER_KEY to barcode String
//            val pressedKey: Char = e.unicodeChar.toChar()
//            Barcode += pressedKey
//            count++
////            LogUtils.e(count)
////            eventCalling(count)
//        }
//        return super.dispatchKeyEvent(e)
//    }
//
//    fun eventCalling(c: Int){
//        if (c == 1){
//            Handler().postDelayed(
//                    {
//                        mainViewModel.getEmployeeDetails(Barcode,this)
//                        ToastUtils.showLong(Barcode)
//                        Barcode = ""
//                        count=0
////                            }
//                    }, 2000)
//        }
//    }

    override fun onPause() {
        super.onPause()
        dialog?.dismiss()
    }

    override fun onBackPressed() {

    }

}
