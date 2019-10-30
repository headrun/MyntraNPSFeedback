package com.mieone.feedbackcollection.viewmodel;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010/\u001a\u0002002\u0006\u00101\u001a\u0002022\u0006\u00103\u001a\u000204J\u000e\u00105\u001a\u0002002\u0006\u00103\u001a\u000204J\u0018\u00106\u001a\u0002002\u0006\u00107\u001a\u0002082\u0006\u00103\u001a\u000204H\u0002J\u0016\u00109\u001a\u0002002\u0006\u0010:\u001a\u00020\f2\u0006\u00103\u001a\u000204J\u000e\u0010;\u001a\u0002002\u0006\u00103\u001a\u000204J\u0016\u0010<\u001a\u0002002\u0006\u0010=\u001a\u00020\f2\u0006\u0010>\u001a\u00020?J\u0016\u0010@\u001a\u0002002\u0006\u00107\u001a\u0002082\u0006\u00103\u001a\u000204J \u0010A\u001a\u0002002\u0006\u00103\u001a\u0002042\u0006\u0010:\u001a\u00020\f2\u0006\u0010B\u001a\u00020CH\u0003R\u001a\u0010\u0005\u001a\u00020\u0006X\u0080\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001c\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001c\u0010\u0011\u001a\u0004\u0018\u00010\fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u000e\"\u0004\b\u0013\u0010\u0010R\u001c\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u001c\u0010\u001a\u001a\u0004\u0018\u00010\fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u000e\"\u0004\b\u001c\u0010\u0010R\u0011\u0010\u001d\u001a\u00020\u001e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0014\u0010!\u001a\b\u0012\u0004\u0012\u00020\f0\"X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001c\u0010#\u001a\u0004\u0018\u00010\fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b$\u0010\u000e\"\u0004\b%\u0010\u0010R\u001c\u0010&\u001a\u0004\u0018\u00010\fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\'\u0010\u000e\"\u0004\b(\u0010\u0010R\u001a\u0010)\u001a\u00020\fX\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b*\u0010\u000e\"\u0004\b+\u0010\u0010R\u001c\u0010,\u001a\u0004\u0018\u00010\fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b-\u0010\u000e\"\u0004\b.\u0010\u0010\u00a8\u0006D"}, d2 = {"Lcom/mieone/feedbackcollection/viewmodel/MainViewModel;", "Landroidx/lifecycle/AndroidViewModel;", "application", "Landroid/app/Application;", "(Landroid/app/Application;)V", "count", "", "getCount$app_debug", "()I", "setCount$app_debug", "(I)V", "department", "", "getDepartment", "()Ljava/lang/String;", "setDepartment", "(Ljava/lang/String;)V", "doj", "getDoj", "setDoj", "downloadTask", "Lcom/mieone/feedbackcollection/dialog/DownloadTask;", "getDownloadTask", "()Lcom/mieone/feedbackcollection/dialog/DownloadTask;", "setDownloadTask", "(Lcom/mieone/feedbackcollection/dialog/DownloadTask;)V", "employee_status", "getEmployee_status", "setEmployee_status", "handler", "Landroid/os/Handler;", "getHandler", "()Landroid/os/Handler;", "languageList", "Ljava/util/ArrayList;", "manager", "getManager", "setManager", "name", "getName", "setName", "url", "getUrl", "setUrl", "vendor", "getVendor", "setVendor", "addTextWatcher", "", "et_employee_id", "Landroid/widget/EditText;", "activity", "Landroid/app/Activity;", "anonymousLogin", "getAPKUrl", "databaseReference", "Lcom/google/firebase/database/DatabaseReference;", "getEmployeeDetails", "scanned_id", "getLanguageList", "getUpdate", "verCode", "dialog", "Lcom/yarolegovich/lovelydialog/LovelyStandardDialog;", "requestPermission", "updateInDB", "model", "Lcom/mieone/feedbackcollection/model/EmployeeFeedbackModel;", "app_debug"})
public final class MainViewModel extends androidx.lifecycle.AndroidViewModel {
    private int count;
    private java.util.ArrayList<java.lang.String> languageList;
    @org.jetbrains.annotations.NotNull()
    public java.lang.String url;
    @org.jetbrains.annotations.Nullable()
    private com.mieone.feedbackcollection.dialog.DownloadTask downloadTask;
    @org.jetbrains.annotations.NotNull()
    private final android.os.Handler handler = null;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String name;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String manager;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String vendor;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String department;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String doj;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String employee_status;
    
    public final int getCount$app_debug() {
        return 0;
    }
    
    public final void setCount$app_debug(int p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getUrl() {
        return null;
    }
    
    public final void setUrl(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.mieone.feedbackcollection.dialog.DownloadTask getDownloadTask() {
        return null;
    }
    
    public final void setDownloadTask(@org.jetbrains.annotations.Nullable()
    com.mieone.feedbackcollection.dialog.DownloadTask p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.os.Handler getHandler() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getName() {
        return null;
    }
    
    public final void setName(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getManager() {
        return null;
    }
    
    public final void setManager(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getVendor() {
        return null;
    }
    
    public final void setVendor(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getDepartment() {
        return null;
    }
    
    public final void setDepartment(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getDoj() {
        return null;
    }
    
    public final void setDoj(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getEmployee_status() {
        return null;
    }
    
    public final void setEmployee_status(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    public final void getLanguageList(@org.jetbrains.annotations.NotNull()
    android.app.Activity activity) {
    }
    
    public final void getUpdate(@org.jetbrains.annotations.NotNull()
    java.lang.String verCode, @org.jetbrains.annotations.NotNull()
    com.yarolegovich.lovelydialog.LovelyStandardDialog dialog) {
    }
    
    public final void addTextWatcher(@org.jetbrains.annotations.NotNull()
    android.widget.EditText et_employee_id, @org.jetbrains.annotations.NotNull()
    android.app.Activity activity) {
    }
    
    public final void getEmployeeDetails(@org.jetbrains.annotations.NotNull()
    java.lang.String scanned_id, @org.jetbrains.annotations.NotNull()
    android.app.Activity activity) {
    }
    
    @android.annotation.SuppressLint(value = {"SimpleDateFormat"})
    private final void updateInDB(android.app.Activity activity, java.lang.String scanned_id, com.mieone.feedbackcollection.model.EmployeeFeedbackModel model) {
    }
    
    public final void anonymousLogin(@org.jetbrains.annotations.NotNull()
    android.app.Activity activity) {
    }
    
    public final void requestPermission(@org.jetbrains.annotations.NotNull()
    com.google.firebase.database.DatabaseReference databaseReference, @org.jetbrains.annotations.NotNull()
    android.app.Activity activity) {
    }
    
    private final void getAPKUrl(com.google.firebase.database.DatabaseReference databaseReference, android.app.Activity activity) {
    }
    
    public MainViewModel(@org.jetbrains.annotations.NotNull()
    android.app.Application application) {
        super(null);
    }
}