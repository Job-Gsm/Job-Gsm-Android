package com.example.job_gsm.data.request

import com.google.gson.annotations.SerializedName

data class CheckEmailRequest(
    @SerializedName("email") val email: String,
    @SerializedName("key") val key: String
)
