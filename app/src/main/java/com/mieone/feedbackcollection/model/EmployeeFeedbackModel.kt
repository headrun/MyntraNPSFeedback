package com.mieone.feedbackcollection.model

data class EmployeeFeedbackModel (

    var employee_id: String? = null,
    var superior_experience: String? = null,
    var employee_status: String?=null,
    var name: String? = null,
    var manager: String? = null,
    var vendor: String? = null,
    var department: String? = null,
    var doj: String? = null,

    var check_in_time: Long = 0,
    var feedback_time: Long = 0,
    var check_out_time: Long = 0,

    var isCheckedIn: Boolean = false,
    var isCheckedOut: Boolean = false
)
