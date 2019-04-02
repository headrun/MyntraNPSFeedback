package com.mieone.feedbackcollection;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.airbnb.lottie.LottieAnimationView;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mieone.feedbackcollection.application.MyApplication;
import com.mieone.feedbackcollection.dialog.UpdateDialog;
import com.mieone.feedbackcollection.model.EmployeeFeedbackModel;
import com.mieone.feedbackcollection.utils.ActivityManager;
import com.mieone.feedbackcollection.utils.Constants;
import com.mieone.feedbackcollection.utils.General;
import com.wang.avi.AVLoadingIndicatorView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.lottie) LottieAnimationView lottie;
    @BindView(R.id.et_employee_id) EditText employee_id;
    @BindView(R.id.avi_main) AVLoadingIndicatorView avi;

    EmployeeFeedbackModel model = new EmployeeFeedbackModel();
    String Barcode = "";
    SpinnerDialog languageDialog;
    ArrayList<String> languageList = new ArrayList<>();
    private UpdateDialog dialog;
    String verCode;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        dialog = new UpdateDialog(this);

        init();

//        initializeDialog();

        employee_id.requestFocus();

        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            verCode = String.valueOf(pInfo.versionCode);
//            getUpdate(verCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void init(){

        if (!NetworkUtils.isConnected()){
            General.redDialog(MainActivity.this, "Oops no Internet Connection!", null);
        }

        General.setLang("hindi");

        getLanguageList();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startLockTask();
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
            lottie.setAnimation("scan-animation.json");
            lottie.loop(true);
            lottie.playAnimation();
        }


        languageDialog = new SpinnerDialog(this, languageList, "Select or Search Language", R.style.DialogAnimations_SmileWindow);

        languageDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String s, int i) {

                General.setLang(s);
                ToastUtils.showLong(s);

            }
        });

        General.hideKeyboard(this);

        //add text watcher in edittext

        employee_id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (!NetworkUtils.isConnected()){
                    General.redDialog(MainActivity.this, "Oops no Internet Connection!", null);
                    return;
                }

                new Handler().postDelayed(
                        new Runnable() {
                            @Override
                            public void run() {
                                final String scanned_id = employee_id.getText().toString();
                                LogUtils.e(scanned_id);
                                if (scanned_id.length() >= 4) {
                                    count++;
                                    if (count == 1) {

                                        getEmployeeDetails(scanned_id);

//                                        getLastCheckTime(scanned_id);

                                        General.hideKeyboard(MainActivity.this);

                                        employee_id.getText().clear();
                                        employee_id.requestFocus();

                                        count = 0;
                                    }
                                }
                            }
                        }, 1000);

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        getUpdate(verCode);
    }

    private void getEmployeeDetails(final String scanned_id){

        MyApplication.get().getmFirebaseFirestore().collection(Constants.EMPLOYEE_RECORDS).document(scanned_id)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()){

                    DocumentSnapshot document = task.getResult();

                    if (document.exists()){

                        String name = document.getString("name");
                        String manager = document.getString("manager");
                        String employee_code = document.getString("employee_code");
                        String vendor = document.getString("vendor");
                        String department = document.getString("department");
                        String doj = document.getString("doj");

                        model.setName(name);
                        model.setManager(manager);
                        model.setVendor(vendor);
                        model.setDepartment(department);
                        model.setDoj(doj);

                    }else {

                        Map<String, Object> employeeData = new HashMap<>();
                        employeeData.put("name", " ");
                        employeeData.put("manager", " ");
                        employeeData.put("employee_code", " ");
                        employeeData.put("vendor", " ");
                        employeeData.put("department", " ");
                        employeeData.put("doj", " ");

                        General.redDialog(MainActivity.this, "Not Authorized!! Please contact your manager.", null);

                        MyApplication.get().getmFirebaseFirestore().collection(Constants.EMPLOYEE_RECORDS)
                                .document(scanned_id).set(employeeData);

                    }
                    getLastCheckTime(scanned_id);

                }

            }
        });

    }

    private void getLastCheckTime(final String scanned_id){

        if (!NetworkUtils.isConnected()){
            General.redDialog(MainActivity.this, "Oops no Internet Connection!", null);
            return;
        }

        final long time = System.currentTimeMillis();
        Date date = new Date(time);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        final String getDate = format.format(date);

        MyApplication.get().getmFirebaseFirestore().collection(Constants.EMPLOYEE_FEEDBACK).document(scanned_id)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        LogUtils.e("DocumentSnapshot data: " + document.getLong("last_check_in"));
                        long last_check_in = document.getLong("last_check_in");
                        long runningTime = System.currentTimeMillis() - last_check_in;
                        long mins = TimeUnit.MILLISECONDS.toMinutes(runningTime);
                        LogUtils.e("Mins -> "+ mins);

                        if (mins > 15 * 60){

                            updateCheckInTime(scanned_id, getDate, time);

                        }else {

                            getCheckInTime(scanned_id, last_check_in);
                        }


                    } else {
                        LogUtils.e("No such document");
                        updateCheckInTime(scanned_id, getDate, time);
                    }

                    General.hideKeyboard(MainActivity.this);
                    employee_id.getText().clear();
                    employee_id.requestFocus();

                } else {
                    LogUtils.e("get failed with ", task.getException());
                    General.hideKeyboard(MainActivity.this);
                    employee_id.getText().clear();
                    employee_id.requestFocus();
                }
            }
        });

    }

    private void getCheckInTime(final String scanned_id, long check_in_time){

        MyApplication.get().getmFirebaseFirestore().collection(Constants.EMPLOYEE_FEEDBACK)
                .document(scanned_id).collection(Constants.EMPLOYEE_SESSION).whereEqualTo("check_in_time", check_in_time)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                QuerySnapshot snapshots = task.getResult();

                long check_in_time = snapshots.getDocuments().get(0).getLong("check_in_time");
                long runningTime = System.currentTimeMillis() - check_in_time;

                long mins = TimeUnit.MILLISECONDS.toMinutes(runningTime);

                LogUtils.e("Minutes ->"+ mins);

                if (mins > 6 * 60){

                    if (snapshots.getDocuments().get(0).getBoolean("checkedOut").equals(true)){

                        General.redDialog(MainActivity.this, "Already checked out.", null);

                    }else {

                        ActivityManager.FEEDBACK(MainActivity.this, scanned_id,snapshots.getDocuments().get(0).getId());

                    }
                }else {

                    General.redDialog(MainActivity.this, "Already checked in.", null);
                }

                employee_id.getText().clear();
                employee_id.requestFocus();



            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                General.hideKeyboard(MainActivity.this);
                employee_id.getText().clear();
                employee_id.requestFocus();
            }
        });

    }

    private void updateLastCheckInTime(String scanned_id, long time){

        Map<String, Object> data = new HashMap<>();
        data.put("last_check_in", time);

        MyApplication.get().getmFirebaseFirestore().collection(Constants.EMPLOYEE_FEEDBACK)
                .document(scanned_id).set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                General.hideKeyboard(MainActivity.this);
                employee_id.getText().clear();
                employee_id.requestFocus();
            }
        });
    }

    private void updateCheckInTime(final String scanned_id, String date, final long time){


        General.alertDialog(MainActivity.this, "Checked in Successfully.", null);

        model.setCheck_in_time(time);
        model.setCheckedIn(true);
        model.setEmployee_id(scanned_id);
        model.setCheck_in_date(date);

        MyApplication.get().getmFirebaseFirestore().collection(Constants.EMPLOYEE_FEEDBACK)
                .document(scanned_id).collection(Constants.EMPLOYEE_SESSION).document().set(model)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        updateLastCheckInTime(scanned_id, time);

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                General.hideKeyboard(MainActivity.this);
                employee_id.getText().clear();
                employee_id.requestFocus();
            }
        });

    }

    private void initializeDialog(){

        final CharSequence[] items = { getResources().getString(R.string.hindi), getResources().getString(R.string.marathi),
                getResources().getString(R.string.kannada), getResources().getString(R.string.tamil) };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select language");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals(getResources().getString(R.string.hindi))) {

                    General.setLang("Hindi");
                    ToastUtils.showLong("Hindi selected");
                    dialog.dismiss();

                } else if (items[item].equals(getResources().getString(R.string.marathi))) {

                    General.setLang("Marathi");
                    ToastUtils.showLong("Marathi selected");
                    dialog.dismiss();

                } else if (items[item].equals(getResources().getString(R.string.kannada))) {

                    General.setLang("Kannada");
                    ToastUtils.showLong("Kannada selected");
                    dialog.dismiss();
                }else if (items[item].equals(getResources().getString(R.string.tamil))){

                    General.setLang("Tamil");
                    ToastUtils.showLong("Tamil Selected");
                    dialog.dismiss();
                }
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    private void getLanguageList(){

        MyApplication.get().getmFirebaseFirestore().collection(Constants.FEEDBACK_QUESTION)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                for (QueryDocumentSnapshot document : task.getResult()) {
                    LogUtils.e(document.getId() + " => " + document.getData());

                    languageList.add(document.getId());
                }

                languageList.remove("english");

                languageDialog.showSpinerDialog();
            }
        });
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

    private void getUpdate(final String verCode){

        MyApplication.get().getmFirebaseDataBase().getReference(Constants.APK_Path)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (!dataSnapshot.hasChild(verCode)){
                            dialog.show();
                            dialog.setUpdateBtn(MainActivity.this, MainActivity.this);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
}
