package com.mieone.feedbackcollection.application;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u0000 \u00112\u00020\u0001:\u0001\u0011B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u000b\u001a\u0004\u0018\u00010\u0004J\u0006\u0010\f\u001a\u00020\u0006J\u0006\u0010\r\u001a\u00020\bJ\b\u0010\u000e\u001a\u0004\u0018\u00010\nJ\b\u0010\u000f\u001a\u00020\u0010H\u0016R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2 = {"Lcom/mieone/feedbackcollection/application/MyApplication;", "Landroid/app/Application;", "()V", "mAuth", "Lcom/google/firebase/auth/FirebaseAuth;", "mFirebaseDataBase", "Lcom/google/firebase/database/FirebaseDatabase;", "mFirebaseFirestore", "Lcom/google/firebase/firestore/FirebaseFirestore;", "mFirebaseUser", "Lcom/google/firebase/auth/FirebaseUser;", "getmAuth", "getmFirebaseDataBase", "getmFirebaseFirestore", "getmFirebaseUser", "onCreate", "", "Companion", "app_debug"})
public final class MyApplication extends android.app.Application {
    private com.google.firebase.database.FirebaseDatabase mFirebaseDataBase;
    private com.google.firebase.firestore.FirebaseFirestore mFirebaseFirestore;
    private com.google.firebase.auth.FirebaseUser mFirebaseUser;
    private com.google.firebase.auth.FirebaseAuth mAuth;
    private static com.mieone.feedbackcollection.application.MyApplication instance;
    public static final com.mieone.feedbackcollection.application.MyApplication.Companion Companion = null;
    
    @java.lang.Override()
    public void onCreate() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.google.firebase.database.FirebaseDatabase getmFirebaseDataBase() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.google.firebase.firestore.FirebaseFirestore getmFirebaseFirestore() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.google.firebase.auth.FirebaseAuth getmAuth() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.google.firebase.auth.FirebaseUser getmFirebaseUser() {
        return null;
    }
    
    public MyApplication() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0006"}, d2 = {"Lcom/mieone/feedbackcollection/application/MyApplication$Companion;", "", "()V", "instance", "Lcom/mieone/feedbackcollection/application/MyApplication;", "get", "app_debug"})
    public static final class Companion {
        
        @org.jetbrains.annotations.Nullable()
        public final com.mieone.feedbackcollection.application.MyApplication get() {
            return null;
        }
        
        private Companion() {
            super();
        }
    }
}