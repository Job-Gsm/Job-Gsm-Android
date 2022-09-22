package com.example.job_gsm.model.api.user

import com.example.job_gsm.model.data.request.SignUpRequest
import com.example.job_gsm.model.data.response.SignResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SignUpService {
    @POST("/user/signup")
    fun signUpService(@Body body: SignUpRequest): Call<SignResponse>
}