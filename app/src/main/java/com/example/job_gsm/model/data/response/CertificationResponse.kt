package com.example.job_gsm.model.data.response

import com.google.gson.annotations.SerializedName

data class CertificationResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("status") val status: Int
)
