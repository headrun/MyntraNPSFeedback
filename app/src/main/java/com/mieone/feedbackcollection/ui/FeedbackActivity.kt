package com.mieone.feedbackcollection.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.ToastUtils
import com.mieone.feedbackcollection.R
import com.mieone.feedbackcollection.application.MyApplication
import com.mieone.feedbackcollection.utils.Constants
import com.mieone.feedbackcollection.utils.General
import com.mieone.feedbackcollection.viewmodel.FeedbackViewModel
import kotlinx.android.synthetic.main.activity_feedback.*
import java.text.SimpleDateFormat
import java.util.Date
import spencerstudios.com.bungeelib.Bungee

class FeedbackActivity : AppCompatActivity() {

    private lateinit var feedbackViewModel: FeedbackViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)

        feedbackViewModel = ViewModelProviders.of(this).get(FeedbackViewModel::class.java)

        init()

    }

    private fun init(){
        val extras = intent
        val bundle = extras.extras
        if (bundle == null) {
            ToastUtils.showLong("Some problem occurs.")
            finish()
            return
        }
        feedbackViewModel.employee_id = bundle.getString("employee_id")
        feedbackViewModel.document_id = bundle.getString("document_id")

        img_good.setOnClickListener {

            feedbackViewModel.displayView(0, img_good, img_avg, img_poor, this)

        }

        img_avg.setOnClickListener {

            feedbackViewModel.displayView(1, img_good, img_avg, img_poor, this)
        }

        img_poor.setOnClickListener {

            feedbackViewModel.displayView(2, img_good, img_avg, img_poor, this)
        }

        card_submit.setOnClickListener {
            if (feedbackViewModel.superior_feedback == " ") {

                ToastUtils.showLong("Please give feedback.")

                return@setOnClickListener
            }

            if (!NetworkUtils.isConnected()) {
                ToastUtils.showLong("Oops no network connection! Please connect to the internet.")
                return@setOnClickListener
            }

            card_submit!!.text = "Please wait.."

            val time = System.currentTimeMillis()

            val date = Date(time)
            @SuppressLint("SimpleDateFormat") val format = SimpleDateFormat("dd-MM-yyyy")
            val getDate = format.format(date)

            MyApplication.get()?.getmFirebaseFirestore()?.collection(Constants.EMPLOYEE_FEEDBACK)
                    ?.document(feedbackViewModel.employee_id!!)?.collection(Constants.EMPLOYEE_SESSION)
                    ?.document(feedbackViewModel.document_id!!)
                    ?.update(
                            "check_out_date", getDate,
                            "check_out_time", time,
                            "checkedOut", true,
                            "feedback_time", time,
                            "superior_experience", feedbackViewModel.superior_feedback
                    )?.addOnSuccessListener {
                        ToastUtils.showLong("Thanks for giving feedback")
                        finish()
                        Bungee.slideRight(this@FeedbackActivity)
                    }
            General.hideKeyboard(this)

        }

    }

    override fun onStart() {
        super.onStart()

        feedbackViewModel.getLanguage(multi_language_txt)
    }

    override fun onBackPressed() {

    }
}
