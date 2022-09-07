package com.example.job_gsm.model.data.response

import com.google.gson.annotations.SerializedName

data class Tokens(
    @SerializedName("accessToken") val accessToken: String,
    @SerializedName("refreshToken") val refreshToken: String
)
