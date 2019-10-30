package com.mieone.feedbackcollection.viewmodel;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u001a\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J.\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020%2\u0006\u0010\'\u001a\u00020%2\u0006\u0010(\u001a\u00020)J\u0018\u0010*\u001a\u00020!2\u0006\u0010(\u001a\u00020)2\u0006\u0010+\u001a\u00020\u0006H\u0002J\u000e\u0010,\u001a\u00020!2\u0006\u0010-\u001a\u00020.J\u0010\u0010/\u001a\u00020!2\u0006\u0010(\u001a\u00020)H\u0002R\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001c\u0010\u000b\u001a\u0004\u0018\u00010\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\b\"\u0004\b\r\u0010\nR\u001c\u0010\u000e\u001a\u0004\u0018\u00010\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\b\"\u0004\b\u0010\u0010\nR\u001c\u0010\u0011\u001a\u0004\u0018\u00010\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\b\"\u0004\b\u0013\u0010\nR\u001c\u0010\u0014\u001a\u0004\u0018\u00010\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\b\"\u0004\b\u0016\u0010\nR\u001c\u0010\u0017\u001a\u0004\u0018\u00010\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\b\"\u0004\b\u0019\u0010\nR\u001a\u0010\u001a\u001a\u00020\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\b\"\u0004\b\u001c\u0010\nR\u001c\u0010\u001d\u001a\u0004\u0018\u00010\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\b\"\u0004\b\u001f\u0010\n\u00a8\u00060"}, d2 = {"Lcom/mieone/feedbackcollection/viewmodel/FeedbackViewModel;", "Landroidx/lifecycle/AndroidViewModel;", "application", "Landroid/app/Application;", "(Landroid/app/Application;)V", "department", "", "getDepartment", "()Ljava/lang/String;", "setDepartment", "(Ljava/lang/String;)V", "doj", "getDoj", "setDoj", "employee_status", "getEmployee_status", "setEmployee_status", "manager", "getManager", "setManager", "name", "getName", "setName", "scanned_id", "getScanned_id", "setScanned_id", "superior_feedback", "getSuperior_feedback", "setSuperior_feedback", "vendor", "getVendor", "setVendor", "displayView", "", "position", "", "img_good", "Landroid/widget/ImageView;", "img_avg", "img_poor", "activity", "Landroid/app/Activity;", "getFeedbackSubmitted", "document_id", "getLanguage", "multi_language_txt", "Landroid/widget/TextView;", "updateInDB", "app_debug"})
public final class FeedbackViewModel extends androidx.lifecycle.AndroidViewModel {
    @org.jetbrains.annotations.NotNull()
    private java.lang.String superior_feedback;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String scanned_id;
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
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSuperior_feedback() {
        return null;
    }
    
    public final void setSuperior_feedback(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getScanned_id() {
        return null;
    }
    
    public final void setScanned_id(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
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
    
    public final void displayView(int position, @org.jetbrains.annotations.NotNull()
    android.widget.ImageView img_good, @org.jetbrains.annotations.NotNull()
    android.widget.ImageView img_avg, @org.jetbrains.annotations.NotNull()
    android.widget.ImageView img_poor, @org.jetbrains.annotations.NotNull()
    android.app.Activity activity) {
    }
    
    private final void updateInDB(android.app.Activity activity) {
    }
    
    private final void getFeedbackSubmitted(android.app.Activity activity, java.lang.String document_id) {
    }
    
    public final void getLanguage(@org.jetbrains.annotations.NotNull()
    android.widget.TextView multi_language_txt) {
    }
    
    public FeedbackViewModel(@org.jetbrains.annotations.NotNull()
    android.app.Application application) {
        super(null);
    }
}