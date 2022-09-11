package com.example.job_gsm.model.data.request

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SignUpRequest(
    @SerializedName("username") val username: String,
    @SerializedName("email") var email: String,
    @SerializedName("password") val password: String
): Serializable
