package com.example.job_gsm.data.response.user

import com.google.gson.annotations.SerializedName

data class TokenData(
    @SerializedName("token") val token: Tokens
)
