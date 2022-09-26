package com.example.job_gsm.network.user

import com.example.job_gsm.data.request.ChangePwRequest
import com.example.job_gsm.data.response.user.BaseUserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ChangePwService {
    @POST("user/change/password")
    suspend fun changePw(@Body body: ChangePwRequest): Response<BaseUserResponse>
}