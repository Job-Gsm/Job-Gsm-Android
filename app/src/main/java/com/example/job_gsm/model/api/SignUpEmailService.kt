package com.example.job_gsm.model.api

import com.example.job_gsm.model.data.response.CertificationResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SignUpEmailService {
    @POST("user/signup/send/email")
    fun signInEmail(@Body email: String): Call<CertificationResponse>
}