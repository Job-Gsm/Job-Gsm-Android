package com.example.job_gsm.network.user

import com.example.job_gsm.data.request.SelectMajorRequest
import com.example.job_gsm.data.response.user.BaseUserResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SelectMajorService {
    @POST("user/select/major")
    suspend fun setMajor(@Body body: SelectMajorRequest): Response<BaseUserResponse>
}