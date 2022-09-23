package com.example.job_gsm.data

import com.google.gson.annotations.SerializedName

data class GetAllBoardResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("status") val status: String,
    @SerializedName("result") val result: Content?
)
