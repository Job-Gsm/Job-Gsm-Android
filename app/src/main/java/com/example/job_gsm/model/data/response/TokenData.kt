package com.example.job_gsm.model.data.response

import com.google.gson.annotations.SerializedName

data class TokenData(
    @SerializedName("token") val token: Tokens
)
