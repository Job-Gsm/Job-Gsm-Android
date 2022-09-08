package com.example.job_gsm.view.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.job_gsm.R
import com.example.job_gsm.databinding.ActivityForgotPwBinding
import com.example.job_gsm.viewmodel.ForgetPwViewModel

class ForgotPwActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgotPwBinding
    private val viewModel by viewModels<ForgetPwViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityForgotPwBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.emailCertificationBtn.setOnClickListener {
            binding.emailErrorText.visibility = View.GONE
            newPwSendEmail(binding.forgetEmailEditText.text.toString())
        }
        binding.backLoginText.setOnClickListener { finish() }
    }

    private fun newPwSendEmail(email: String) {
        viewModel.forgetPw(email)
        viewModel.forgetPwServiceLiveData.observe(this, Observer {
            when(it?.success) {
                true -> {
                    val dialogView = LayoutInflater.from(this).inflate(R.layout.custom_dialog_forget_pw_send_email, null)
                    binding.newPwLinear.visibility = View.VISIBLE
                }
                false -> {
                    if (it.status == 404) {
                        binding.emailErrorText.visibility = View.VISIBLE
                        binding.emailErrorText.text = "계정을 찾을 수 없습니다."
                    } else if (it.status == 400) {
                        binding.emailErrorText.visibility = View.VISIBLE
                        binding.emailErrorText.text = "학교계정을 입력해주세요."
                    }
                }
                else -> { return@Observer }
            }
        })
    }
}