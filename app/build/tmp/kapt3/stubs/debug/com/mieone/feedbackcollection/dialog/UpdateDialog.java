package com.mieone.feedbackcollection.dialog;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0001#B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u001b\u001a\u00020\u001cJ\b\u0010\u001d\u001a\u00020\u001cH\u0002J\b\u0010\u001e\u001a\u00020\u001cH\u0002J\u000e\u0010\u001f\u001a\u00020\u001c2\u0006\u0010 \u001a\u00020!J\u0006\u0010\"\u001a\u00020\u001cR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001c\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u0016X\u0080\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001a\u00a8\u0006$"}, d2 = {"Lcom/mieone/feedbackcollection/dialog/UpdateDialog;", "", "context", "Landroid/app/Activity;", "(Landroid/app/Activity;)V", "databaseReference", "Lcom/google/firebase/database/DatabaseReference;", "dialog", "Landroid/app/Dialog;", "downloadManager", "Landroid/app/DownloadManager;", "lottie", "Lcom/airbnb/lottie/LottieAnimationView;", "onComplete", "Landroid/content/BroadcastReceiver;", "getOnComplete", "()Landroid/content/BroadcastReceiver;", "setOnComplete", "(Landroid/content/BroadcastReceiver;)V", "updateBtn", "Landroidx/cardview/widget/CardView;", "url", "", "getUrl$app_debug", "()Ljava/lang/String;", "setUrl$app_debug", "(Ljava/lang/String;)V", "close", "", "getAPKUrl", "init", "setUpdateBtn", "activity", "Lcom/mieone/feedbackcollection/ui/MainActivity;", "show", "DownloadFile", "app_debug"})
public final class UpdateDialog {
    private android.app.Dialog dialog;
    private androidx.cardview.widget.CardView updateBtn;
    private com.airbnb.lottie.LottieAnimationView lottie;
    private com.google.firebase.database.DatabaseReference databaseReference;
    private android.app.DownloadManager downloadManager;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String url;
    @org.jetbrains.annotations.Nullable()
    private android.content.BroadcastReceiver onComplete;
    private final android.app.Activity context = null;
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getUrl$app_debug() {
        return null;
    }
    
    public final void setUrl$app_debug(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.content.BroadcastReceiver getOnComplete() {
        return null;
    }
    
    public final void setOnComplete(@org.jetbrains.annotations.Nullable()
    android.content.BroadcastReceiver p0) {
    }
    
    private final void init() {
    }
    
    public final void setUpdateBtn(@org.jetbrains.annotations.NotNull()
    com.mieone.feedbackcollection.ui.MainActivity activity) {
    }
    
    public final void show() {
    }
    
    public final void close() {
    }
    
    private final void getAPKUrl() {
    }
    
    public UpdateDialog(@org.jetbrains.annotations.NotNull()
    android.app.Activity context) {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0006\u0018\u00002\u0014\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0005J%\u0010\f\u001a\u00020\u00022\u0016\u0010\r\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00020\u000e\"\u0004\u0018\u00010\u0002H\u0014\u00a2\u0006\u0002\u0010\u000fJ\u0012\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0002H\u0014J\b\u0010\u0013\u001a\u00020\u0011H\u0014J%\u0010\u0014\u001a\u00020\u00112\u0016\u0010\u0015\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00020\u000e\"\u0004\u0018\u00010\u0002H\u0014\u00a2\u0006\u0002\u0010\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000b\u00a8\u0006\u0017"}, d2 = {"Lcom/mieone/feedbackcollection/dialog/UpdateDialog$DownloadFile;", "Landroid/os/AsyncTask;", "", "context", "Landroid/app/Activity;", "(Landroid/app/Activity;)V", "progressDialog", "Landroid/app/ProgressDialog;", "getProgressDialog", "()Landroid/app/ProgressDialog;", "setProgressDialog", "(Landroid/app/ProgressDialog;)V", "doInBackground", "params", "", "([Ljava/lang/String;)Ljava/lang/String;", "onPostExecute", "", "result", "onPreExecute", "onProgressUpdate", "values", "([Ljava/lang/String;)V", "app_debug"})
    public static final class DownloadFile extends android.os.AsyncTask<java.lang.String, java.lang.String, java.lang.String> {
        @org.jetbrains.annotations.Nullable()
        private android.app.ProgressDialog progressDialog;
        private android.app.Activity context;
        
        @org.jetbrains.annotations.Nullable()
        public final android.app.ProgressDialog getProgressDialog() {
            return null;
        }
        
        public final void setProgressDialog(@org.jetbrains.annotations.Nullable()
        android.app.ProgressDialog p0) {
        }
        
        @java.lang.Override()
        protected void onPreExecute() {
        }
        
        @org.jetbrains.annotations.NotNull()
        @java.lang.Override()
        protected java.lang.String doInBackground(@org.jetbrains.annotations.NotNull()
        java.lang.String... params) {
            return null;
        }
        
        @java.lang.Override()
        protected void onProgressUpdate(@org.jetbrains.annotations.NotNull()
        java.lang.String... values) {
        }
        
        @java.lang.Override()
        protected void onPostExecute(@org.jetbrains.annotations.Nullable()
        java.lang.String result) {
        }
        
        public DownloadFile(@org.jetbrains.annotations.NotNull()
        android.app.Activity context) {
            super();
        }
    }
}