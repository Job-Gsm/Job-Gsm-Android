package com.example.job_gsm.view.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.job_gsm.databinding.ActivitySignInBinding
import com.example.job_gsm.view.MainActivity
import com.example.job_gsm.viewmodel.SignInViewModel

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private val viewModel by viewModels<SignInViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setStatusBarTranslucent(true)

        binding.signInText.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        binding.logInBtn.setOnClickListener {
            binding.emailInputLayout.isErrorEnabled = false
            binding.pwInputLayout.isErrorEnabled = false
            login(binding.loginId.text.toString(), binding.loginPw.text.toString())
        }
    }

    private fun login(email: String, pw: String) {
        val emailInputLay = binding.emailInputLayout
        val pwInputLay = binding.pwInputLayout

        viewModel.signInObserver(email, pw)
        viewModel.signInServiceLiveData.observe(this, Observer {
            if (it != null) {
                when(it.message) {
                    "계정을 찾을 수 없습니다." -> {
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
            } else {
                Log.e("TAG", "login: ${it?.status}")
            }
        })
    }

    private fun setStatusBarTranslucent(makeTranslucent: Boolean) {
        if (makeTranslucent) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
}