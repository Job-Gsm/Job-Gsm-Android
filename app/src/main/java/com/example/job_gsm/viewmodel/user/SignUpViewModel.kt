package com.example.job_gsm.viewmodel.user

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.job_gsm.model.ApiClient
import com.example.job_gsm.model.api.user.SignUpService
import com.example.job_gsm.model.data.request.SignUpRequest
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

class SignUpViewModel: ViewModel() {
    companion object {
        private const val TAG = "SignUpViewModel"
    }

    var signUpService: SignUpService
    var signUpServiceLiveData: MutableLiveData<SignResponse?> = MutableLiveData()

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
        signUpService.signUpService(SignUpRequest(username, email, pw)).enqueue(object :Callback<SignResponse> {
            override fun onResponse(call: Call<SignResponse>, response: Response<SignResponse>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "onResponse: ${response.body()?.status}")
                    signUpServiceLiveData.value = response.body()
                } else {
                    val jsonErrorObj = JSONObject(response.errorBody()!!.string())
                    val status = jsonErrorObj.getString("status")
                    val message = jsonErrorObj.getString("message")
                    val signUpResponse = SignResponse(false, status, message, null)

                    signUpServiceLiveData.value = signUpResponse
                    Log.d(TAG, "onResponse else: $signUpResponse")
                }
            }

            override fun onFailure(call: Call<SignResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}", t.cause)
                signUpServiceLiveData.value = null
            }
        })
    }
}