package com.example.job_gsm.network.user

import com.example.job_gsm.data.response.user.BaseUserResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SendEmailService {
    @POST("user/send/email")
    suspend fun sendEmail(@Body email: String): Response<BaseUserResponse>
}