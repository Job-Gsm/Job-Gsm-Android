package com.example.job_gsm.model.data.response

import com.google.gson.annotations.SerializedName

data class SignUpResponse(
    @SerializedName("success") val success: Boolean?,
    @SerializedName("status") val status: String,
    @SerializedName("message") val message: String
)
