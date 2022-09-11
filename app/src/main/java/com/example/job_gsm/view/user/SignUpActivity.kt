package com.example.job_gsm.view.user

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.job_gsm.databinding.ActivitySignUpBinding
import com.example.job_gsm.model.data.request.SignUpRequest
import com.example.job_gsm.viewmodel.SignUpViewModel

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private val viewModel by viewModels<SignUpViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signInFinishBtn.setOnClickListener {
            binding.nicknameInputLayout.isErrorEnabled = false
            binding.pwInputLayout.isErrorEnabled = false
            binding.pwCheckInputLayout.isErrorEnabled = false

            ifEditTextIsEmpty()
        }
    }

    private fun ifEditTextIsEmpty() {
        if (binding.signInNickName.text.toString().isNotEmpty() && binding.signInPw.text.toString().isNotEmpty()) {
            startActivity(Intent(this, InputEmailActivity::class.java)
                .putExtra("data", SignUpRequest(binding.signInNickName.text.toString(), "", binding.signInPw.text.toString())))
        } else if (binding.signInNickName.text.toString().isEmpty() && binding.signInPw.text.toString().isEmpty()) {
            binding.pwInputLayout.error = "필수 입력사항입니다."
            binding.nicknameInputLayout.error = "필수 입력사항입니다."
            return
        } else if (binding.signInPw.text.toString().isEmpty()) {
            binding.pwInputLayout.error = "필수 입력사항입니다."
            return
        } else if (binding.signInNickName.text.toString().isEmpty()) {
            binding.nicknameInputLayout.error = "필수 입력사항입니다."
            return
        }
    }

}