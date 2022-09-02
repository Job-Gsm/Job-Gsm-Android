package com.example.job_gsm.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.job_gsm.model.ApiClient
import com.example.job_gsm.model.api.SignUpService
import com.example.job_gsm.model.data.request.SignUpRequest
import com.example.job_gsm.model.data.response.SignUpResponse
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RegistrationViewModel: ViewModel() {
    companion object {
        private const val TAG = "RegistrationViewModel"
    }

    var signUpService: SignUpService
    var signUpServiceLiveData: MutableLiveData<SignUpResponse?> = MutableLiveData()

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        signUpService = Retrofit.Builder()
            .baseUrl(ApiClient.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
            .create(SignUpService::class.java)
    }

    fun signUpObserver(username: String, email: String, pw: String) {
        Log.d(TAG, "signUpObserver: ${username}, ${email}, $pw")
        signUpService.signUpService(SignUpRequest(username, email, pw)).enqueue(object :Callback<SignUpResponse> {
            override fun onResponse(call: Call<SignUpResponse>, response: Response<SignUpResponse>) {
                Log.d(TAG, "onResponse: ${response.body()}")
                signUpServiceLiveData.value = response.body()
            }

            override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}", t.cause)
                signUpServiceLiveData.value = null
            }
        })
    }
}