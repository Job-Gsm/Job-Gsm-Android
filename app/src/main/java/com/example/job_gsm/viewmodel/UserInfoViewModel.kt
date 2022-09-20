package com.example.job_gsm.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.job_gsm.model.ApiClient
import com.example.job_gsm.model.api.UserInfoService
import com.example.job_gsm.model.data.request.UserInfoRequest
import com.example.job_gsm.model.data.response.BaseUserResponse
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserInfoViewModel: ViewModel() {
    var userInfoService: UserInfoService
    var userInfoLiveData: MutableLiveData<BaseUserResponse?> = MutableLiveData()

    companion object {
        private const val TAG = "UserInfoViewModel"
    }

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        userInfoService = Retrofit.Builder()
            .baseUrl(ApiClient.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
            .create(UserInfoService::class.java)
    }

    fun setUserInfo(email: String, username: String, github: String, discord: String) {
        userInfoService.setUserInfo(UserInfoRequest(email, username, github, discord)).enqueue(object :Callback<BaseUserResponse> {
            override fun onResponse(call: Call<BaseUserResponse>, response: Response<BaseUserResponse>) {
                if (response.isSuccessful) {
                    userInfoLiveData.value = response.body()
                } else {
                    val jsonErrorObj = JSONObject(response.errorBody()!!.string())
                    Log.d("TAG", "onResponse 400: $jsonErrorObj")
                    val status = jsonErrorObj.getString("status")
                    val message = jsonErrorObj.getString("message")

                    userInfoLiveData.value = BaseUserResponse(false, message, status)
                }
            }

            override fun onFailure(call: Call<BaseUserResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}, ${t.stackTrace}", t.cause)
            }
        })
    }
}