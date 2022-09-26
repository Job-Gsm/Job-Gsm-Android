package com.example.job_gsm.repository

import android.app.Application
import com.example.job_gsm.data.response.board.GetAllBoardResponse
import com.example.job_gsm.network.board.BoardObject
import org.json.JSONObject
import retrofit2.Response

class GetAllBoardRepository(application: Application) {
    // singleton pattern
    companion object {
        private var instance: GetAllBoardRepository? = null

        fun getInstance(application: Application): GetAllBoardRepository? {
            if (instance == null) instance = GetAllBoardRepository(application)
            return instance
        }
    }

    // retrofit
    // get all board
    suspend fun getAllBoard(page: Int): GetAllBoardResponse {
        val response = BoardObject.getAllBoardService.getAllBoard(page)
        return if (response.isSuccessful) {
            response.body() as GetAllBoardResponse
        } else {
            val jsonErrorObj = JSONObject(response.errorBody()!!.string())
            val status = jsonErrorObj.getString("status")
            val message = jsonErrorObj.getString("message")

            GetAllBoardResponse(false, message = message, status = status, null)
        }
    }
}