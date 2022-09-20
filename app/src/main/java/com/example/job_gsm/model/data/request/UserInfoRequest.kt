package com.example.job_gsm.model.data.request

import com.google.gson.annotations.SerializedName

data class UserInfoRequest(
    @SerializedName("email") val email: String,
    @SerializedName("username") val username: String,
    @SerializedName("github") val github: String,
    @SerializedName("discord") val discord: String
)
