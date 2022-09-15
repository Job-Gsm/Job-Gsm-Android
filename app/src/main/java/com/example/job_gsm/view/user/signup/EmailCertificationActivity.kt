package com.example.job_gsm.view.user.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.job_gsm.R
import com.example.job_gsm.databinding.ActivityEmailCertificationBinding

class EmailCertificationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEmailCertificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEmailCertificationBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}