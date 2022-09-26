package com.example.job_gsm.data.request

import com.google.gson.annotations.SerializedName

data class ChangePwRequest(
    @SerializedName("email") val email: String,
    @SerializedName("newPassword") val newPassword: String
)
