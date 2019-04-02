package com.mieone.feedbackcollection.model;

public class EmployeeFeedbackModel {

    private String employee_id, superior_experience, check_in_date, check_out_date, name,
            manager, vendor, department, doj;

    private long check_in_time, feedback_time, check_out_time;

    private boolean isCheckedIn, isCheckedOut;

    public String getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }

    public String getSuperior_experience() {
        return superior_experience;
    }

    public void setSuperior_experience(String superior_experience) {
        this.superior_experience = superior_experience;
    }

    public long getCheck_in_time() {
        return check_in_time;
    }

    public void setCheck_in_time(long check_in_time) {
        this.check_in_time = check_in_time;
    }

    public long getFeedback_time() {
        return feedback_time;
    }

    public void setFeedback_time(long feedback_time) {
        this.feedback_time = feedback_time;
    }

    public boolean isCheckedIn() {
        return isCheckedIn;
    }

    public void setCheckedIn(boolean checkedIn) {
        isCheckedIn = checkedIn;
    }

    public long getCheck_out_time() {
        return check_out_time;
    }

    public void setCheck_out_time(long check_out_time) {
        this.check_out_time = check_out_time;
    }

    public boolean isCheckedOut() {
        return isCheckedOut;
    }

    public void setCheckedOut(boolean checkedOut) {
        isCheckedOut = checkedOut;
    }

    public String getCheck_in_date() {
        return check_in_date;
    }

    public void setCheck_in_date(String check_in_date) {
        this.check_in_date = check_in_date;
    }

    public String getCheck_out_date() {
        return check_out_date;
    }

    public void setCheck_out_date(String check_out_date) {
        this.check_out_date = check_out_date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDoj() {
        return doj;
    }

    public void setDoj(String doj) {
        this.doj = doj;
    }
}
