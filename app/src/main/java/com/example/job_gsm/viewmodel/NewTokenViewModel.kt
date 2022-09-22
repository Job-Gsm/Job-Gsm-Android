package com.example.job_gsm.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.job_gsm.model.ApiClient
import com.example.job_gsm.model.api.NewTokenService
import com.example.job_gsm.model.data.response.NewTokenResponse
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.sign

class NewTokenViewModel: ViewModel() {
    companion object {
        private const val TAG = "NewTokenViewModel"
    }

    var newTokenService: NewTokenService
    val newTokenLiveData: MutableLiveData<NewTokenResponse?> = MutableLiveData()

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        newTokenService = Retrofit.Builder()
            .baseUrl(ApiClient.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
            .create(NewTokenService::class.java)
    }

    fun issuedToken(email: String) {
        newTokenService.issuedToken(email).enqueue(object :Callback<NewTokenResponse> {
            override fun onResponse(call: Call<NewTokenResponse>, response: Response<NewTokenResponse>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "onResponse set new token: success")
                    newTokenLiveData.value = response.body()
                } else {
                    val jsonErrorObj = JSONObject(response.errorBody()!!.string())
                    val status = jsonErrorObj.getString("status")
                    val message = jsonErrorObj.getString("message")

                    val newTokenResponse = NewTokenResponse(null, status, message, null)
                    newTokenLiveData.value = newTokenResponse
                }
            }

            override fun onFailure(call: Call<NewTokenResponse>, t: Throwable) {
                Log.e("FAIL", "onFailure: ${t.message}, ${t.stackTrace}", t.cause)
                newTokenLiveData.value = null
            }
        })
    }
}