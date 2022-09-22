package com.example.job_gsm.viewmodel.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.job_gsm.model.ApiClient
import com.example.job_gsm.model.api.user.ForgetPwService
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

class ForgetPwViewModel: ViewModel() {
    var forgetPwService: ForgetPwService
    var forgetPwServiceLiveData: MutableLiveData<BaseUserResponse?> = MutableLiveData()

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        forgetPwService = Retrofit.Builder()
            .baseUrl(ApiClient.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
            .create(ForgetPwService::class.java)
    }

    fun forgetPw(email: String) {
        forgetPwService.forgetPw(email).enqueue(object :Callback<BaseUserResponse> {
            override fun onResponse(call: Call<BaseUserResponse>, response: Response<BaseUserResponse>) {
                if (response.isSuccessful) {
                    forgetPwServiceLiveData.value = response.body()
                } else {
                    val jsonErrorObj = JSONObject(response.errorBody()!!.string())
                    val status = jsonErrorObj.getString("status")
                    val message = jsonErrorObj.getString("message")

                    val baseUserResponse = BaseUserResponse(false, status, message)
                    forgetPwServiceLiveData.value = baseUserResponse
                }
            }

            override fun onFailure(call: Call<BaseUserResponse>, t: Throwable) {
                forgetPwServiceLiveData.value = null
            }
        })
    }
}