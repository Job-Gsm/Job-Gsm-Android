package com.example.job_gsm.data.response.user

import com.google.gson.annotations.SerializedName

data class AccessToken(
    @SerializedName("accessToken") val accessToken: String
)
