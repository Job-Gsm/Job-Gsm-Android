package com.example.job_gsm.repository

import android.app.Application
import android.util.Log
import com.example.job_gsm.data.request.*
import com.example.job_gsm.data.response.user.BaseUserResponse
import com.example.job_gsm.data.response.user.NewTokenResponse
import com.example.job_gsm.data.response.user.UserResponse
import com.example.job_gsm.network.user.UserObject
import com.example.job_gsm.viewmodel.user.SignInViewModel
import org.json.JSONObject

class UserRepository(application: Application) {
    // singleton pattern
    companion object {
        private var instance: UserRepository? = null

        fun getInstance(application: Application): UserRepository? {
            if (instance == null) instance = UserRepository(application)
            return instance
        }
    }

    // retrofit
    // sign in
    suspend fun signIn(email: String, pw: String): UserResponse {
        val response = UserObject.signInService.signIn(SignInRequest(email, pw))
        return if (response.isSuccessful) {
            response.body() as UserResponse
        } else {
            val jsonErrorObj = JSONObject(response.errorBody()!!.string())
            val status = jsonErrorObj.getString("status")
            val message = jsonErrorObj.getString("message")
            val signInResponse = UserResponse(null, status, message, null)
            Log.d(SignInViewModel.TAG, "onResponse: $signInResponse")

            UserResponse(null, status, message, null)
        }
    }

    // sign up
    suspend fun signUp(username: String, email: String, pw: String): UserResponse {
        val response = UserObject.signUpService.signUpService(SignUpRequest(username, email, pw))
        return if (response.isSuccessful) {
            Log.d("TAG", "onResponse: ${response.body()?.status}")
            response.body() as UserResponse
        } else {
            val jsonErrorObj = JSONObject(response.errorBody()!!.string())
            val status = jsonErrorObj.getString("status")
            val message = jsonErrorObj.getString("message")
            val signUpResponse = UserResponse(false, status, message, null)
            Log.d("TAG", "onResponse else: $signUpResponse")

            signUpResponse
        }
    }

    // new token
    suspend fun newToken(email: String): NewTokenResponse {
        val response = UserObject.newTokenService.issuedToken(email)
        return if (response.isSuccessful) {
            Log.d("TAG", "onResponse set new token: success")
            response.body() as NewTokenResponse
        } else {
            val jsonErrorObj = JSONObject(response.errorBody()!!.string())
            val status = jsonErrorObj.getString("status")
            val message = jsonErrorObj.getString("message")
            val newTokenResponse = NewTokenResponse(null, status, message, null)
            Log.d("TAG", "newToken: $newTokenResponse")

            newTokenResponse
        }
    }

    // send email
    suspend fun sendEmail(email: String): BaseUserResponse {
        val response = UserObject.sendEmailService.sendEmail(email)
        return if (response.isSuccessful) {
            response.body() as BaseUserResponse
        } else {
            val jsonErrorObj = JSONObject(response.errorBody()!!.string())
            Log.d("TAG", "onResponse 400: $jsonErrorObj")
            val status = jsonErrorObj.getString("status")
            val message = jsonErrorObj.getString("message")

            BaseUserResponse(false, message, status)
        }
    }

    // check email
    suspend fun checkEmail(body: CheckEmailRequest): BaseUserResponse {
        val response = UserObject.checkEmailService.checkPw(body)
        return if (response.isSuccessful) {
            response.body() as BaseUserResponse
        } else {
            val jsonErrorObj = JSONObject(response.errorBody()!!.string())
            val status = jsonErrorObj.getString("status")
            val message = jsonErrorObj.getString("message")

            BaseUserResponse(false, message, status)
        }
    }

    // user information
    suspend fun getUserInfo(email: String, username: String, github: String, discord: String): BaseUserResponse {
        val response = UserObject.userInfoService.setUserInfo(UserInfoRequest(email, username, github, discord))
        return if (response.isSuccessful) {
            response.body() as BaseUserResponse
        } else {
            val jsonErrorObj = JSONObject(response.errorBody()!!.string())
            Log.d("TAG", "onResponse 400: $jsonErrorObj")
            val status = jsonErrorObj.getString("status")
            val message = jsonErrorObj.getString("message")

            BaseUserResponse(false, message, status)
        }
    }

    // select major
    suspend fun selectMajor(email: String, major: String, career: Int): BaseUserResponse {
        val response = UserObject.selectMajorService.setMajor(SelectMajorRequest(email, major, career))
        return if (response.isSuccessful) {
            response.body() as BaseUserResponse
        } else {
            val jsonErrorObj = JSONObject(response.errorBody()!!.string())
            val status = jsonErrorObj.getString("status")
            val message = jsonErrorObj.getString("message")

            BaseUserResponse(false, status, message)
        }
    }

    // change password
    suspend fun changePw(email: String, newPw: String): BaseUserResponse {
        val response = UserObject.changePwService.changePw(ChangePwRequest(email, newPw))
        return if (response.isSuccessful) {
            response.body() as BaseUserResponse
        } else {
            val jsonErrorObj = JSONObject(response.errorBody()!!.string())
            val status = jsonErrorObj.getString("status")
            val message = jsonErrorObj.getString("message")

            BaseUserResponse(false, status, message)
        }
    }
}