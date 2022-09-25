package com.example.job_gsm.network.user

import com.example.job_gsm.data.request.UserInfoRequest
import com.example.job_gsm.data.response.user.BaseUserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserInfoService {
    @POST("user/information")
    suspend fun setUserInfo(@Body body: UserInfoRequest): Response<BaseUserResponse>
}