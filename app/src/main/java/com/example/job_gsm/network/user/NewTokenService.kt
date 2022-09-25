package com.example.job_gsm.network.user

import com.example.job_gsm.data.response.user.NewTokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PUT

interface NewTokenService {
    @PUT("refreshToken")
    suspend fun issuedToken(@Body email: String): Response<NewTokenResponse>
}