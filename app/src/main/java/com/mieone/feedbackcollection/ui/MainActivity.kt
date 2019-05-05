package com.mieone.feedbackcollection.ui

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.NetworkUtils
import com.mieone.feedbackcollection.dialog.UpdateDialog
import com.mieone.feedbackcollection.utils.General
import androidx.lifecycle.ViewModelProviders
import com.mieone.feedbackcollection.R
import com.mieone.feedbackcollection.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    internal var Barcode = ""
    private var dialog: UpdateDialog? = null
    private lateinit var verCode: String
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        dialog = UpdateDialog(this)

        init()

        et_employee_id.requestFocus()

        try {
            val pInfo = this.packageManager.getPackageInfo(packageName, 0)
            val version = pInfo.versionName
            verCode = pInfo.versionCode.toString()
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

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

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
            lottie.setAnimation("scan-animation.json")
            lottie.loop(true)
            lottie.playAnimation()
        }

        General.hideKeyboard(this)

        //add text watcher in edittext
        mainViewModel.addTextWatcher(et_employee_id, this)


    }

    override fun onResume() {
        super.onResume()

        dialog?.let { mainViewModel.getUpdate(verCode, it, this) }
    }

    //    @Override
    //    public boolean dispatchKeyEvent(KeyEvent e) {
    //
    //        if(e.getAction()==KeyEvent.ACTION_DOWN
    //                && e.getKeyCode() != KeyEvent.KEYCODE_ENTER){ //Not Adding ENTER_KEY to barcode String
    //            char pressedKey = (char) e.getUnicodeChar();
    //            Barcode += pressedKey;
    //        }
    //        if (e.getAction()==KeyEvent.ACTION_DOWN
    //                && e.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
    //            LogUtils.e("Barcode Read: "+Barcode);
    //            getEmployeeDetails(Barcode);
    //            getLastCheckTime(Barcode);
    //            Barcode="";
    //        }
    //        return false;
    //    }

}
