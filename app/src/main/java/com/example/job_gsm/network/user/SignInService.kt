package com.example.job_gsm.network.user

import com.example.job_gsm.data.request.SignInRequest
import com.example.job_gsm.data.response.user.UserResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SignInService {
    @POST("/user/signin")
    suspend fun signIn(@Body body: SignInRequest): Response<UserResponse>
}