package com.mieone.feedbackcollection.dialog

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.app.DownloadManager
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.view.View

import androidx.cardview.widget.CardView

import com.airbnb.lottie.LottieAnimationView
import com.blankj.utilcode.util.ToastUtils
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
import com.mieone.feedbackcollection.utils.Constants

class UpdateDialog(private val context: Context) {
    private var dialog: Dialog? = null
    private var updateBtn: CardView? = null
    private var lottie: LottieAnimationView? = null
    private var databaseReference: DatabaseReference? = null
    private lateinit var downloadManager: DownloadManager
    internal var url: String? = null

    init {
        init()
    }

    private fun init() {
        dialog = Dialog(context)
        dialog!!.setContentView(R.layout.dialog_update)
        dialog!!.setCancelable(false)

        updateBtn = dialog!!.findViewById(R.id.card_force_update)
        lottie = dialog!!.findViewById(R.id.lottie)

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

    fun setUpdateBtn(context: Context, activity: Activity) {

        // onClick on Update Button
        updateBtn!!.setOnClickListener {
            Dexter.withActivity(activity)
                    .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .withListener(object : MultiplePermissionsListener {
                        override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                            if (report.areAllPermissionsGranted()) {
                                getAPKUrl()
                            } else {
                            }

                        }

                        override fun onPermissionRationaleShouldBeShown(permissions: List<PermissionRequest>, token: PermissionToken) {
                            token.continuePermissionRequest()
                            //                                ActivityManager.PERMISSION_TAB(PermissionActivity.this);
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

                    val uri = Uri.parse(url)
                    val request = DownloadManager.Request(uri)
                    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE or DownloadManager.Request.NETWORK_WIFI)
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    request.setTitle("Feeder App")
                    request.setVisibleInDownloadsUi(true)
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, uri.lastPathSegment)
                    (context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager).enqueue(request)
                }

                close()
                ToastUtils.showLong("Please close the application to install the updated version")


            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }
}
