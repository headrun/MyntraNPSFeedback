package com.mieone.feedbackcollection.dialog;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v7.widget.CardView;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.blankj.utilcode.util.ToastUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.mieone.feedbackcollection.R;
import com.mieone.feedbackcollection.model.UpdateModel;
import com.mieone.feedbackcollection.utils.Constants;

import java.util.List;

public class UpdateDialog {

    private Context context;
    private Dialog dialog;
    private CardView updateBtn;
    private LottieAnimationView lottie;
    private DatabaseReference databaseReference;
    DownloadManager downloadManager;
    String url;

    public UpdateDialog(Context context) {
        this.context = context;
        init();
    }

    private void init(){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_update);
        dialog.setCancelable(false);

        updateBtn = dialog.findViewById(R.id.card_force_update);
        lottie = dialog.findViewById(R.id.lottie);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
            lottie.setAnimation("red_alert.json");
            lottie.loop(true);
            lottie.playAnimation();
        }

        downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

        dialog.setCanceledOnTouchOutside(false);

        if(dialog.getWindow() != null) {
//            dialog.getWindow().getAttributes().windowAnimations = R.style.Slide_Up_Down;
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        }
    }

    public void setUpdateBtn(final Context context, final Activity activity){

        // onClick on Update Button
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(activity)
                        .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                if(report.areAllPermissionsGranted()){
                                    getAPKUrl();
                                }else {
                                }

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                                token.continuePermissionRequest();
//                                ActivityManager.PERMISSION_TAB(PermissionActivity.this);
                            }
                        }).check();
            }
        });

    }

    public void show(){
        try {
            dialog.show();
        } catch (Exception e){

        }
    }

    public void close(){
        dialog.hide();
    }

    private void getAPKUrl() {

        databaseReference = FirebaseDatabase.getInstance().getReference(Constants.APK_Path);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {


                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    UpdateModel updateModel = dataSnapshot.getValue(UpdateModel.class);
                    updateModel.getUrl();

                    url = updateModel.getUrl();

                }

                if (url != null) {

                    Uri uri = Uri.parse(url);
                    DownloadManager.Request request = new DownloadManager.Request(uri);
                    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE |
                            DownloadManager.Request.NETWORK_WIFI);
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    request.setTitle("Feeder App");
                    request.setVisibleInDownloadsUi(true);
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, uri.getLastPathSegment());
                    ((DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE)).enqueue(request);
                }

                close();
                ToastUtils.showLong("Please close the application to install the updated version");


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
