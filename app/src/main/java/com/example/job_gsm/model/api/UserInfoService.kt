package com.example.job_gsm.model.api

import com.example.job_gsm.model.data.request.UserInfoRequest
import com.example.job_gsm.model.data.response.BaseUserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserInfoService {
    @POST("user/information")
    fun setUserInfo(@Body body: UserInfoRequest): Call<BaseUserResponse>
}