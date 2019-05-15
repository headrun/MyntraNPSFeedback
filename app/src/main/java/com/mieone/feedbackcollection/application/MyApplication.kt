package com.mieone.feedbackcollection.application

import android.app.Application

import com.blankj.utilcode.util.Utils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

import io.paperdb.Paper
import android.os.StrictMode



class MyApplication : Application() {
    private var mFirebaseDataBase: FirebaseDatabase? = null
    private var mFirebaseFirestore: FirebaseFirestore? = null
    internal var mFirebaseUser: FirebaseUser? = null
    private var mAuth: FirebaseAuth? = null

    override fun onCreate() {
        super.onCreate()
        instance = this
        Paper.init(applicationContext)
        Utils.init(this)
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)

        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
    }

    fun getmFirebaseDataBase(): FirebaseDatabase {

        if (mFirebaseDataBase == null)
            mFirebaseDataBase = FirebaseDatabase.getInstance()

        return mFirebaseDataBase as FirebaseDatabase
    }

    fun getmFirebaseFirestore(): FirebaseFirestore {
        if (mFirebaseFirestore == null)
            mFirebaseFirestore = FirebaseFirestore.getInstance()
        return mFirebaseFirestore as FirebaseFirestore
    }

    fun getmAuth(): FirebaseAuth? {
        if (mAuth == null) {
            mAuth = FirebaseAuth.getInstance()
        }
        return mAuth
    }

    fun getmFirebaseUser(): FirebaseUser? {
        if (mFirebaseUser == null) {
            mFirebaseUser = mAuth!!.currentUser
        }
        return mFirebaseUser
    }

    companion object {

        private var instance: MyApplication? = null

        fun get(): MyApplication? {
            return instance
        }
    }
}
