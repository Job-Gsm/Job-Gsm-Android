package com.example.job_gsm.network.user

import com.example.job_gsm.data.ApiClient
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object UserObject {
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()
    
    private val userRetrofit by lazy {
        Retrofit.Builder()
            .baseUrl(ApiClient.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // sign in
    val signInService: SignInService by lazy {
        userRetrofit.create(SignInService::class.java)
    }
    
    // sign up
    val signUpService: SignUpService by lazy {
        userRetrofit.create(SignUpService::class.java)
    }

    // new token
    val newTokenService: NewTokenService by lazy {
        userRetrofit.create(NewTokenService::class.java)
    }

    // send email
    val sendEmailService: SendEmailService by lazy {
        userRetrofit.create(SendEmailService::class.java)
    }

    // check email
    val checkEmailService: CheckEmailService by lazy {
        userRetrofit.create(CheckEmailService::class.java)
    }

    //user information
    val userInfoService: UserInfoService by lazy {
        userRetrofit.create(UserInfoService::class.java)
    }

    // select major
    val selectMajorService: SelectMajorService by lazy {
        userRetrofit.create(SelectMajorService::class.java)
    }

    // change password
    val changePwService: ChangePwService by lazy {
        userRetrofit.create(ChangePwService::class.java)
    }
}