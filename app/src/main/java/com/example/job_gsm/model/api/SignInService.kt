package com.example.job_gsm.model.api

import com.example.job_gsm.model.data.request.SignInRequest
import com.example.job_gsm.model.data.response.SignResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SignInService {
    @POST("/user/signin")
    fun signIn(@Body body: SignInRequest): Call<SignResponse>
}