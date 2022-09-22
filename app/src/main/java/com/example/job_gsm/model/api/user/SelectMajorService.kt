package com.example.job_gsm.model.api.user

import com.example.job_gsm.model.data.request.SelectMajorRequest
import com.example.job_gsm.model.data.response.BaseUserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SelectMajorService {
    @POST("user/select/major")
    fun setMajor(@Body body: SelectMajorRequest): Call<BaseUserResponse>
}