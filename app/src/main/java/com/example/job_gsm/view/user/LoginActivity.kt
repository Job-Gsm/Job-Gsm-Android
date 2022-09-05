package com.example.job_gsm.view.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.job_gsm.databinding.ActivityLoginBinding
import com.example.job_gsm.viewmodel.SignInViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel by viewModels<SignInViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setStatusBarTranslucent(true)

        binding.signInText.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }

        binding.logInBtn.setOnClickListener {
            login(binding.loginId.text.toString(), binding.loginPw.text.toString())
        }
    }

    private fun login(email: String, pw: String) {
        val emailInputLay = binding.emailInputLayout
        val pwInputLay = binding.pwInputLayout

        viewModel.signInServiceLiveData.observe(this, Observer {
            if (it != null) {
                when(it.message) {
                    "계정을 찾을 수 없습니다." -> {
                        emailInputLay.isErrorEnabled = true
                        emailInputLay.error = "계정을 찾을 수 없습니다."
                    }
                    "비밀번호가 맞지 않습니다." -> {
                        pwInputLay.isErrorEnabled = true
                        pwInputLay.error = "비밀번호가 맞지 않습니다."
                    }
                    "email : 이메일 형식이 아닙니다" -> {
                        emailInputLay.isErrorEnabled = true
                        emailInputLay.error = "이메일 형식이 다릅니다."
                    }
                    "password : 크기가 10에서 20 사이여야 합니다" -> {
                        pwInputLay.isErrorEnabled = true
                        pwInputLay.error = "길이가 10자~20자 사이여야 합니다."
                    }
                    "email : 이메일 형식이 아닙니다, password : 크기가 10에서 20 사이여야 합니다" -> {
                        emailInputLay.isErrorEnabled = true
                        pwInputLay.isErrorEnabled = true
                        emailInputLay.error = "이메일 형식이 다릅니다."
                        pwInputLay.error = "길이가 10자~20자 사이여야 합니다."
                    }
                    else -> {
//                        startActivity(Intent(this, ))
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