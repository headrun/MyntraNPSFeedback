package com.mieone.feedbackcollection.application;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import io.paperdb.Paper;

public class MyApplication extends Application {

    private static MyApplication instance = null;
    private FirebaseDatabase mFirebaseDataBase;
    private FirebaseFirestore mFirebaseFirestore;
    FirebaseUser mFirebaseUser;
    private FirebaseAuth mAuth;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Paper.init(getApplicationContext());
        Utils.init(this);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

    public static MyApplication get() {
        return instance;
    }

    public FirebaseDatabase getmFirebaseDataBase(){

        if (mFirebaseDataBase == null)
            mFirebaseDataBase = FirebaseDatabase.getInstance();

        return mFirebaseDataBase;
    }

    public FirebaseFirestore getmFirebaseFirestore() {
        if (mFirebaseFirestore == null)
            mFirebaseFirestore = FirebaseFirestore.getInstance();
        return mFirebaseFirestore;
    }

    public FirebaseAuth getmAuth() {
        if (mAuth == null) {
            mAuth = FirebaseAuth.getInstance();
        }
        return mAuth;
    }

    public FirebaseUser getmFirebaseUser() {
        if (mFirebaseUser == null) {
            mFirebaseUser = mAuth.getCurrentUser();
        }
        return mFirebaseUser;
    }
}
