package com.mieone.feedbackcollection;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.storage.StorageReference;
import com.mieone.feedbackcollection.application.MyApplication;
import com.mieone.feedbackcollection.model.EmployeeFeedbackModel;
import com.mieone.feedbackcollection.utils.Constants;
import com.mieone.feedbackcollection.utils.General;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.grantland.widget.AutofitTextView;
import spencerstudios.com.bungeelib.Bungee;

public class FeedbackActivity extends AppCompatActivity {

    @BindView(R.id.img_good) ImageView img_good;
    @BindView(R.id.img_avg) ImageView img_avg;
    @BindView(R.id.img_poor) ImageView img_poor;
    @BindView(R.id.card_submit) Button card_submit;
    @BindView(R.id.service_text) AutofitTextView service_text;
    @BindView(R.id.multi_language_txt) AutofitTextView multi_language_txt;
    @BindColor(R.color.white) int white;
    @BindColor(R.color.black) int black;
    String superior_feedback = " ";

    String employee_id, document_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ButterKnife.bind(this);

        Intent extras = getIntent();
        Bundle bundle = extras.getExtras();
        if(bundle == null){
            ToastUtils.showLong("Some problem occurs.");
            finish();
            return;
        }
        employee_id = bundle.getString("employee_id");
        document_id = bundle.getString("document_id");

    }

    @Override
    protected void onStart() {
        super.onStart();

        getLanguage();

//        if (General.getLang() != null){
//
//            if (General.getLang().equals("Hindi")){
//
//                multi_language_txt.setText(getResources().getString(R.string.hindi_ques));
//            }
//            else if (General.getLang().equals("Marathi")){
//
//                multi_language_txt.setText(getResources().getString(R.string.marathi_ques));
//            }
//            else if (General.getLang().equals("Kannada")){
//
//                multi_language_txt.setText(getResources().getString(R.string.kannada_ques));
//            }
//            else if (General.getLang().equals("Tamil")){
//
//                multi_language_txt.setText(getResources().getString(R.string.tamil_ques));
//            }
//        }
    }

    @Override
    public void onBackPressed() {

    }

    public void displayView(int position) {

        switch (position) {
            case 0:
                superior_feedback = "Good";
                img_good.setImageResource(R.drawable.ic_good);
                img_avg.setImageResource(R.drawable.ic_uncheck_avg);
                img_poor.setImageResource(R.drawable.ic_uncheck_poor);
                getFeedbackSubmitted();
                break;

            case 1:
                superior_feedback = "Average";
                img_avg.setImageDrawable(getResources().getDrawable(R.drawable.ic_average));
                img_good.setImageResource(R.drawable.ic_uncheck_good);
                img_poor.setImageResource(R.drawable.ic_uncheck_poor);
                getFeedbackSubmitted();
                break;

            case 2:
                superior_feedback = "Poor";
                img_poor.setImageResource(R.drawable.ic_poor);
                img_good.setImageResource(R.drawable.ic_uncheck_good);
                img_avg.setImageResource(R.drawable.ic_uncheck_avg);
                getFeedbackSubmitted();
                break;

        }

    }

    @OnClick(R.id.img_good)
    void onImgGood(){

        displayView(0);

    }
    @OnClick(R.id.img_avg)
    void onImgAvg(){

        displayView(1);

    }
    @OnClick(R.id.img_poor)
    void onImgPoor(){

        displayView(2);

    }

    private void getFeedbackSubmitted(){

        long time = System.currentTimeMillis();

        Date date = new Date(time);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        final String getDate = format.format(date);

        MyApplication.get().getmFirebaseFirestore().collection(Constants.EMPLOYEE_FEEDBACK)
                .document(employee_id).collection(Constants.EMPLOYEE_SESSION).document(document_id)
                .update(
                        "check_out_date", getDate,
                        "check_out_time", time,
                        "checkedOut", true,
                        "feedback_time", time,
                        "superior_experience", superior_feedback
                );
        ToastUtils.showLong("Thanks for giving feedback");
        finish();
        Bungee.slideRight(FeedbackActivity.this);
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//
//                ToastUtils.showLong("Thanks for giving feedback");
//                finish();
//                Bungee.slideRight(FeedbackActivity.this);
//            }
//        });
    }

    @OnClick(R.id.card_submit)
    void onCardSubmit(){

        if (superior_feedback.equals(" ")){

            ToastUtils.showLong("Please give feedback.");

            return;
        }

        if (!NetworkUtils.isConnected()){
            ToastUtils.showLong("Oops no network connection! Please connect to the internet.");
            return;
        }

        card_submit.setText("Please wait..");

        long time = System.currentTimeMillis();

        Date date = new Date(time);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        final String getDate = format.format(date);

        MyApplication.get().getmFirebaseFirestore().collection(Constants.EMPLOYEE_FEEDBACK)
                .document(employee_id).collection(Constants.EMPLOYEE_SESSION).document(document_id)
                .update(
                        "check_out_date", getDate,
                        "check_out_time", time,
                        "checkedOut", true,
                        "feedback_time", time,
                        "superior_experience", superior_feedback
                ).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                ToastUtils.showLong("Thanks for giving feedback");
                finish();
                Bungee.slideRight(FeedbackActivity.this);
            }
        });
        General.hideKeyboard(this);


    }

    private void getLanguage(){

        MyApplication.get().getmFirebaseFirestore().collection(Constants.FEEDBACK_QUESTION).document(General.getLang())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                DocumentSnapshot document = task.getResult();

                String question = document.getString("question");

                multi_language_txt.setText(question);

            }
        });
    }
}
