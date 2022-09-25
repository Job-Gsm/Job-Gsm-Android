package com.example.job_gsm.view.user

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.job_gsm.databinding.ActivityForgotPwBinding

class ForgotPwActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgotPwBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityForgotPwBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.emailCertificationBtn.setOnClickListener {
            binding.emailStatusText.visibility = View.GONE
        }

        binding.changePwBtn.setOnClickListener {
            // 비밀번호 변경 로직
        }

        binding.backLoginText.setOnClickListener { finish() }
    }
}