package com.example.job_gsm.model.api

import com.example.job_gsm.model.data.response.CertificationResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ForgetPwService {
    @POST("/user/password/send/email")
    fun forgetPw(@Body email: String): Call<CertificationResponse>
}