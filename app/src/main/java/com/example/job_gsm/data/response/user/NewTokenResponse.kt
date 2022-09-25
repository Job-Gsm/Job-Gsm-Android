package com.example.job_gsm.data.response.user

import com.google.gson.annotations.SerializedName

data class NewTokenResponse(
    @SerializedName("success") val success: Boolean?,
    @SerializedName("message") val message: String,
    @SerializedName("status") val status: String,
    @SerializedName("result") val result: AccessToken?
)
