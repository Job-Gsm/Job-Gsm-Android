package com.example.job_gsm.view.user

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.job_gsm.databinding.ActivityInputEmailBinding
import com.example.job_gsm.model.data.request.SignUpRequest

class InputEmailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInputEmailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityInputEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val signUpRequest = intent.getSerializableExtra("data") as SignUpRequest
        Log.d("TAG", "onCreate: $signUpRequest")

        binding.registerEmailBtn.setOnClickListener {
            binding.certificationEmailErrorText.visibility = View.INVISIBLE

            if (binding.certificationEmailEditText.text.isEmpty()) {
                binding.certificationEmailErrorText.visibility = View.VISIBLE
                binding.certificationEmailErrorText.text = "필수 입력항목입니다."
            } else {
                signUpRequest.email = binding.certificationEmailEditText.text.toString()
                Log.d("TAG", "onCreate signup request: $signUpRequest")
                startActivity(Intent(this, EmailCertificationActivity::class.java)
                    .putExtra("signRequest", signUpRequest))
            }
        }
    }
}