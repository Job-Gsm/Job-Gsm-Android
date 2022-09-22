package com.example.job_gsm.view.user.signup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.job_gsm.databinding.ActivityInputEmailBinding
import com.example.job_gsm.model.data.request.SignUpRequest
import com.example.job_gsm.viewmodel.user.SignUpEmailViewModel

class InputEmailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInputEmailBinding
    private val viewModel by viewModels<SignUpEmailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityInputEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val signUpRequest = intent.getSerializableExtra("data") as SignUpRequest
        Log.d("TAG", "onCreate signUpRequest: $signUpRequest")

        binding.registerEmailBtn.setOnClickListener {
            binding.certificationEmailErrorText.visibility = View.INVISIBLE

            if (binding.certificationEmailEditText.text.isEmpty()) {
                binding.certificationEmailErrorText.visibility = View.VISIBLE
                binding.certificationEmailErrorText.text = "필수 입력항목입니다."
            } else {
                viewModel.signUpSendEmail(binding.certificationEmailEditText.text.toString())
                viewModel.signUpEmailServiceLiveData.observe(this, Observer {
                    when(it?.message) {
                        "email : 학교계정을 입력해주세요" -> {
                            binding.certificationEmailErrorText.text = "학교계정을 입력하세요"
                        }
                        else -> {
                            signUpRequest.email = binding.certificationEmailEditText.text.toString()
                            Log.d("TAG", "onCreate signup request: $signUpRequest")
                            startActivity(Intent(this, EmailCertificationActivity::class.java)
                                .putExtra("signRequest", signUpRequest))
                        }
                    }
                })
            }
        }
    }
}