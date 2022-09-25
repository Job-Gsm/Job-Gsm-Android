package com.example.job_gsm.network.user

import com.example.job_gsm.data.response.user.BaseUserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface CheckEmailService {
    @POST("user/check/email")
    suspend fun checkPw(@Body key: String): Response<BaseUserResponse>
}