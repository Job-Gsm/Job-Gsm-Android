package com.example.job_gsm.network.user

import com.example.job_gsm.data.request.SignUpRequest
import com.example.job_gsm.data.response.user.UserResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SignUpService {
    @POST("/user/signup")
    suspend fun signUpService(@Body body: SignUpRequest): Response<UserResponse>
}