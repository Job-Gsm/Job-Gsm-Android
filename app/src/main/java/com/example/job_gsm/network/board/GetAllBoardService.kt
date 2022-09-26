package com.example.job_gsm.network.board

import com.example.job_gsm.data.response.board.GetAllBoardResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GetAllBoardService {
    @GET("user/board?")
    suspend fun getAllBoard(@Query("page") page: Int): Response<GetAllBoardResponse>
}