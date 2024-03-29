package com.example.job_gsm.data.response.user

import com.google.gson.annotations.SerializedName

data class BaseUserResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("status") val status: String
)
