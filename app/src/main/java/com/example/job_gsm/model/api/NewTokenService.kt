package com.example.job_gsm.model.api

import com.example.job_gsm.model.data.response.NewTokenResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.PUT

interface NewTokenService {
    @PUT("refreshToken")
    fun issuedToken(@Body email: String): Call<NewTokenResponse>
}