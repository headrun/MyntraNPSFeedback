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

        img_good.setOnClickListener {

            feedbackViewModel.displayView(0, img_good, img_avg, img_poor, this)

        }

        img_avg.setOnClickListener {

            feedbackViewModel.displayView(1, img_good, img_avg, img_poor, this)
        }

        img_poor.setOnClickListener {

            feedbackViewModel.displayView(2, img_good, img_avg, img_poor, this)
        }

    }

    override fun onStart() {
        super.onStart()

        feedbackViewModel.getLanguage(multi_language_txt)
    }

    override fun onBackPressed() {

    }
}
