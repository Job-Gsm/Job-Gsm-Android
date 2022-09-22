package com.example.job_gsm.viewmodel.user

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.job_gsm.model.ApiClient
import com.example.job_gsm.model.api.user.SelectMajorService
import com.example.job_gsm.model.data.request.SelectMajorRequest
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

class SelectMajorViewModel: ViewModel() {
    companion object {
        private const val TAG = "SelectMajorViewModel"
    }

    var selectMajorService: SelectMajorService
    val selectMajorLiveData: MutableLiveData<BaseUserResponse?> = MutableLiveData()

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        selectMajorService = Retrofit.Builder()
            .baseUrl(ApiClient.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
            .create(SelectMajorService::class.java)
    }

    fun setMajor(email: String, major: String, career: Int) {
        selectMajorService.setMajor(SelectMajorRequest(email, major, career)).enqueue(object :Callback<BaseUserResponse?> {
            override fun onResponse(call: Call<BaseUserResponse?>, response: Response<BaseUserResponse?>) {
                if (response.isSuccessful) {
                    selectMajorLiveData.value = response.body()
                } else {
                    val jsonErrorObj = JSONObject(response.errorBody()!!.string())
                    val status = jsonErrorObj.getString("status")
                    val message = jsonErrorObj.getString("message")
                    val baseUserResponse = BaseUserResponse(false, status, message)

                    selectMajorLiveData.value = baseUserResponse
                }
            }

            override fun onFailure(call: Call<BaseUserResponse?>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}, ${t.stackTrace}", t.cause)
                selectMajorLiveData.value = null
            }
        })
    }
}