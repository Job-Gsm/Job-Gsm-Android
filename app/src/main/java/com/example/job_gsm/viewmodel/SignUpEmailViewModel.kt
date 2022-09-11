package com.example.job_gsm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.job_gsm.model.ApiClient
import com.example.job_gsm.model.api.SignUpEmailService
import com.example.job_gsm.model.data.response.CertificationResponse
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SignUpEmailViewModel: ViewModel() {
    var signUpEmailService: SignUpEmailService
    var signUpEmailServiceLiveData: MutableLiveData<CertificationResponse?> = MutableLiveData()

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        signUpEmailService = Retrofit.Builder()
            .baseUrl(ApiClient.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
            .create(SignUpEmailService::class.java)
    }

    fun signUpSendEmail(email: String) {
        signUpEmailService.signInEmail(email).enqueue(object :Callback<CertificationResponse> {
            override fun onResponse(call: Call<CertificationResponse>, response: Response<CertificationResponse>) {
                if (response.isSuccessful) {
                    signUpEmailServiceLiveData.value = response.body()
                } else {
                    val jsonErrorObj = JSONObject(response.errorBody()!!.string())
                    val status = jsonErrorObj.getString("status")
                    val message = jsonErrorObj.getInt("message")

                    val certificationResponse = CertificationResponse(false, status, message, null)
                    signUpEmailServiceLiveData.value = certificationResponse
                }
            }

            override fun onFailure(call: Call<CertificationResponse>, t: Throwable) {
                signUpEmailServiceLiveData.value = null
            }
        })
    }
}