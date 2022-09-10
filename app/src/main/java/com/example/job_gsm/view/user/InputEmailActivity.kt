package com.example.job_gsm.view.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.job_gsm.R
import com.example.job_gsm.databinding.ActivityInputEmailBinding

class InputEmailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInputEmailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityInputEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val signInRequest = intent.getSerializableExtra("data")
        Log.d("TAG", "onCreate: $signInRequest")

        binding.registerEmailBtn.setOnClickListener {
            startActivity(Intent(this, EmailCertificationActivity::class.java))
        }
    }
}