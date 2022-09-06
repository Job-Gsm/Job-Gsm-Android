package com.example.job_gsm.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.job_gsm.model.ApiClient
import com.example.job_gsm.model.api.SignInService
import com.example.job_gsm.model.data.request.SignInRequest
import com.example.job_gsm.model.data.response.SignResponse
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SignInViewModel: ViewModel() {
    companion object {
        const val TAG = "SignInViewModel"
    }

    var signInService: SignInService
    var signInServiceLiveData: MutableLiveData<SignResponse?> = MutableLiveData()

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        signInService = Retrofit.Builder()
            .baseUrl(ApiClient.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
            .create(SignInService::class.java)
    }

    fun signInObserver(email: String, pw: String) {
        signInService.signIn(SignInRequest(email, pw)).enqueue(object :Callback<SignResponse> {
            override fun onResponse(call: Call<SignResponse>, response: Response<SignResponse>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "onResponse: ${response.body()}")
                    signInServiceLiveData.postValue(response.body())
                } else {
                    val jsonErrorObj = JSONObject(response.errorBody()!!.string())
                    val status = jsonErrorObj.getString("status")
                    val message = jsonErrorObj.getString("message")
                    val signInResponse = SignResponse(null, status, message, null)

                    signInServiceLiveData.value = signInResponse
                    Log.d(TAG, "onResponse: $signInResponse")
                }
            }

            override fun onFailure(call: Call<SignResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}", t.cause)
                signInServiceLiveData.postValue(null)
            }
        })
    }
}