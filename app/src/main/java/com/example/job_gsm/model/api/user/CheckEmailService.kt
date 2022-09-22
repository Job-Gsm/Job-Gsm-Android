package com.example.job_gsm.model.api.user

import com.example.job_gsm.model.data.response.BaseUserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface CheckEmailService {
    @POST("user/check/email")
    fun checkPw(@Body key: String): Call<BaseUserResponse>
}