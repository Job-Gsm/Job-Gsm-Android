package com.example.job_gsm.data.request

import com.google.gson.annotations.SerializedName

data class SelectMajorRequest(
    @SerializedName("email") val email: String,
    @SerializedName("major") val major: String,
    @SerializedName("career") val career: Int
)
