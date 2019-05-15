package com.mieone.feedbackcollection.dialog

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.app.DownloadManager
import android.app.ProgressDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.os.Environment
import android.util.Log
import android.widget.ProgressBar
import androidx.cardview.widget.CardView
import com.airbnb.lottie.LottieAnimationView
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.github.jlmd.animatedcircleloadingview.AnimatedCircleLoadingView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.mieone.feedbackcollection.R
import com.mieone.feedbackcollection.model.UpdateModel
import com.mieone.feedbackcollection.ui.MainActivity
import com.mieone.feedbackcollection.utils.Constants
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.net.URL
import java.net.URLConnection
import java.text.SimpleDateFormat
import java.util.*

class UpdateDialog(private val context: Activity) {
    private var dialog: Dialog? = null
    private var updateBtn: CardView? = null
    private var lottie: LottieAnimationView? = null
    private var databaseReference: DatabaseReference? = null
    private lateinit var downloadManager: DownloadManager
    internal var url: String? = null
    var onComplete: BroadcastReceiver? = null

    init {
        init()
        onComplete = object : BroadcastReceiver() {
            override fun onReceive(ctxt: Context, intent: Intent) {
                ToastUtils.showLong("Download completed")

            }
        }
    }

    private fun init() {
        dialog = Dialog(context)
        dialog!!.setContentView(R.layout.dialog_update)
        dialog!!.setCancelable(false)

        updateBtn = dialog!!.findViewById(R.id.card_force_update)
        lottie = dialog!!.run { this.findViewById(R.id.lottie) }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
            lottie!!.setAnimation("red_alert.json")
            lottie!!.loop(true)
            lottie!!.playAnimation()
        }

        downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        dialog!!.setCanceledOnTouchOutside(false)

        if (dialog!!.window != null) {
            //            dialog.getWindow().getAttributes().windowAnimations = R.style.Slide_Up_Down;
            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
        }
    }

    fun setUpdateBtn(activity: MainActivity) {
        // onClick on Update Button
        updateBtn!!.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                activity.stopLockTask()
            }
            Dexter.withActivity(activity)
                    .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .withListener(object : MultiplePermissionsListener {
                        override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                            if (report.areAllPermissionsGranted()) {
                                LogUtils.e("Clicking...")
                                getAPKUrl()
                            } else {
                            }

                        }

                        override fun onPermissionRationaleShouldBeShown(permissions: List<PermissionRequest>, token: PermissionToken) {
                            token.continuePermissionRequest()
                        }
            }).check()
        }

    }

    fun show() {
        try {
            dialog!!.show()
        } catch (e: Exception) {

        }

    }

    fun close() {
        dialog!!.hide()
    }

    private fun getAPKUrl() {

        databaseReference = FirebaseDatabase.getInstance().getReference(Constants.APK_Path)

        databaseReference!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {


                for (dataSnapshot in snapshot.children) {

                    val updateModel = dataSnapshot.getValue(UpdateModel::class.java)
                    updateModel!!.url

                    url = updateModel.url

                }

                if (url != null) {

                    DownloadFile(context).execute(url)
//                    val uri = Uri.parse(url)
//                    val request = DownloadManager.Request(uri)
//                    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE or DownloadManager.Request.NETWORK_WIFI)
//                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
//                    request.setTitle("NPS Feedback")
//                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, uri.lastPathSegment)
//                    (context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager).enqueue(request)
//                    context.registerReceiver(onComplete, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
                }
//                close()
//                ToastUtils.showLong("Please close the application to install the updated version")


            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }

    class DownloadFile(private var context: Activity) : AsyncTask<String, String, String>() {

        var progressDialog: ProgressDialog?=null

        override fun onPreExecute() {
            super.onPreExecute()
            progressDialog = ProgressDialog(context)
            this.progressDialog!!.setMessage("Progress start")
            this.progressDialog!!.show()
        }

        override fun doInBackground(vararg params: String?): String {

             val count: Int?
            try {
                val url = URL(params[0])
                val connection = url.openConnection()
                connection.connect()
                // getting file length
                val lengthOfFile = connection.getContentLength()


                // input stream to read file - with 8k buffer
                val input =  BufferedInputStream(url.openStream(), 8192)

                val timestamp =  SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format( Date())

                //Extract file name from URL
                var fileName = params[0]?.length?.let { params[0]?.lastIndexOf('/')?.plus(1)?.let { it1 -> params[0]?.substring(it1, it) } }

                //Append timestamp to file name
                fileName = timestamp + "_" + fileName

                //External directory path to save file
                val folder = "${Environment.getExternalStorageDirectory()}/${File.separator}/nps_feedback"

                //Create androiddeft folder if it does not exist
                val directory =  File(folder)

                if (!directory.exists()) {
                    directory.mkdirs()
                }

                // Output stream to write file
                val output =  FileOutputStream(folder + fileName)

                val data =  ByteArray(1024)

                var total = 0
                count = input.read(data)
                while (count != -1) {
                    total += count
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress("" +  ((total * 100) / lengthOfFile))
                    LogUtils.e( "Progress: " + ((total * 100) / lengthOfFile))

                    // writing data to file
                    output.write(data, 0, count)
                }

                // flushing output
                output.flush()

                // closing streams
                output.close()
                input.close()
                return "Downloaded at: $folder$fileName"

            } catch (e : java.lang.Exception) {
                Log.e("Error: ", e.message)
            }

            return "Something went wrong"
        }

        override fun onProgressUpdate(vararg values: String?) {

            progressDialog?.progress = values[0]?.toInt()!!
        }

        override fun onPostExecute(result: String?) {

            this.progressDialog?.dismiss()
        }

    }
}
