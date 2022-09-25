package com.example.job_gsm.view.user

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.job_gsm.databinding.ActivitySignInBinding
import com.example.job_gsm.view.MainActivity
import com.example.job_gsm.view.user.signup.SignUpActivity
import com.example.job_gsm.viewmodel.NewTokenViewModel
import com.example.job_gsm.viewmodel.user.SignInViewModel

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding

    private val viewModel by lazy {
        ViewModelProvider(this, SignInViewModel.Factory(application, email, pw))[SignInViewModel::class.java] }
    private val newTokenViewModel by lazy {
        ViewModelProvider(this, NewTokenViewModel.Factory(application, email))[NewTokenViewModel::class.java] }

    private lateinit var email: String
    private lateinit var pw: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signInText.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        binding.logInBtn.setOnClickListener {
            binding.emailInputLayout.isErrorEnabled = false
            binding.pwInputLayout.isErrorEnabled = false

            email = binding.loginId.text.toString()
            pw = binding.loginPw.text.toString()
            if (email.isEmpty()) {
                binding.emailInputLayout.error = "필수 입력사항입니다."
                return@setOnClickListener
            }
            if (pw.isEmpty()) {
                binding.pwInputLayout.error = "필수 입력사항입니다."
                return@setOnClickListener
            }
            login()
        }

        binding.forgetText.setOnClickListener {
            startActivity(Intent(this, ForgotPwActivity::class.java))
        }
    }

    private fun login() {
        val emailInputLay = binding.emailInputLayout
        val pwInputLay = binding.pwInputLayout

        viewModel.signInServiceLiveData.observe(this, Observer { response ->
            if (response != null) {
                when(response.message) {
                    "사용자를 찾을 수 없습니다." -> {
                        emailInputLay.error = "계정을 찾을 수 없습니다."
                    }
                    "비밀번호가 일치하지 않습니다." -> {
                        pwInputLay.error = "비밀번호가 일치하지 않습니다."
                    }
                    "email : 학교계정을 입력해주세요" -> {
                        emailInputLay.error = "학교계정을 입력해 주세요."
                    }
                    "email : 이메일 형식이 아닙니다" -> {
                        emailInputLay.error = "이메일 형식이 다릅니다."
                    }
                    "password : 크기가 4에서 15 사이여야 합니다" -> {
                        pwInputLay.error = "길이가 4자~15자 사이여야 합니다."
                    }
                    "password : 크기가 4에서 15 사이여야 합니다, email : 학교계정을 입력해주세요" -> {
                        emailInputLay.error = "학교계정을 입력해 주세요."
                        pwInputLay.error = "길이가 4자~15자 사이여야 합니다."
                    }
                    "만료된 토큰입니다." -> {
                        setToken()
                    }
                    "성공" -> {
                        startActivity(Intent(this, MainActivity::class.java)
                            .putExtra("accessToken", response.result?.token?.accessToken))
                        Log.d("TAG", "login: ${response.result?.token?.refreshToken}")
                    }

                    // 엑세스 토큰 만료시간 3시간
                    // refreshToken 만료시간 2주
                }
            }
        })
    }

    private fun setToken() {
        newTokenViewModel.newTokenLiveData.observe(this, Observer { response ->
            if (response?.success == true) {
                Log.d("TAG", "setToken 발급된 새 토큰: ${response.result?.accessToken}")
            } else {
                binding.emailInputLayout.error = "학교계정을 입력해주세요"
                Log.d("TAG", "setToken failed: $response")
            }
        })
    }
}