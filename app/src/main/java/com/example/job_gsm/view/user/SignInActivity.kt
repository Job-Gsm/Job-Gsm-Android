package com.example.job_gsm.view.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.job_gsm.databinding.ActivitySignInBinding
import com.example.job_gsm.view.MainActivity
import com.example.job_gsm.view.user.signup.SignUpActivity
import com.example.job_gsm.viewmodel.SignInViewModel

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private val viewModel by viewModels<SignInViewModel>()

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
            ifEditTextIsEmpty()
        }

        binding.forgetText.setOnClickListener {
            startActivity(Intent(this, ForgotPwActivity::class.java))
        }
    }

    private fun ifEditTextIsEmpty() {
        if (binding.loginId.text.toString().isNotEmpty() && binding.loginPw.text.toString().isNotEmpty()) {
            login(binding.loginId.text.toString(), binding.loginPw.text.toString())
        } else if (binding.loginId.text.toString().isEmpty() && binding.loginPw.text.toString().isEmpty()) {
            binding.emailInputLayout.error = "필수 입력사항입니다."
            binding.pwInputLayout.error = "필수 입력사항입니다."
            return
        } else if (binding.loginId.text.toString().isEmpty()) {
            binding.emailInputLayout.error = "필수 입력사항입니다."
            return
        }  else if (binding.loginPw.text.toString().isEmpty()) {
            binding.pwInputLayout.error = "필수 입력사항입니다."
            return
        }
    }

    private fun login(email: String, pw: String) {
        val emailInputLay = binding.emailInputLayout
        val pwInputLay = binding.pwInputLayout

        viewModel.signInObserver(email, pw)
        viewModel.signInServiceLiveData.observe(this, Observer {
            if (it != null) {
                when(it.message) {
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
                    "성공" -> {
                        startActivity(Intent(this, MainActivity::class.java))
                        Log.d("TAG", "login: ${it.result?.token?.accessToken}")
                    }
                }
            }
        })
    }
}