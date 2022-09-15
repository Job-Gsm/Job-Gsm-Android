package com.example.job_gsm.model.api

import com.example.job_gsm.model.data.response.CertificationResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface CheckEmailService {
    @POST("user/check/email")
    fun checkPw(@Body key: String): Call<CertificationResponse>
}