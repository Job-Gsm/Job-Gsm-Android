package com.example.job_gsm.view.user.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.job_gsm.databinding.ActivityGetUserInfoBinding
import com.example.job_gsm.model.data.request.SignUpRequest
import com.example.job_gsm.viewmodel.user.SignUpViewModel
import com.example.job_gsm.viewmodel.user.UserInfoViewModel

class GetUserInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGetUserInfoBinding
    private val signUpViewModel by viewModels<SignUpViewModel>()
    private val userInfoViewModel by viewModels<UserInfoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGetUserInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val signUpResponse = intent.getSerializableExtra("user") as SignUpRequest

        val errorDiscord = binding.errorDiscordText
        val errorGithub = binding.errorGithubText
        val errorUsername = binding.errorNickNameText
        binding.setUserInfoDoneBtn.setOnClickListener {
            errorDiscord.visibility = View.INVISIBLE
            errorGithub.visibility = View.INVISIBLE
            errorUsername.visibility = View.INVISIBLE

            if (binding.discordEditText.text.isEmpty()) {
                errorDiscord.visibility = View.VISIBLE
                errorDiscord.text = "필수 입력 항목입니다."
            }

            if (binding.githubEditText.text.isEmpty()) {
                errorGithub.visibility = View.VISIBLE
                errorGithub.text = "필수 입력 항목입니다."
            }

            if (binding.nickNameEditText.text.isEmpty()) {
                errorUsername.visibility = View.VISIBLE
                errorUsername.text = "필수 입력 항목입니다."
            }
            signUp(signUpResponse)
        }
    }

    private fun signUp(signUpResponse: SignUpRequest) {
        signUpViewModel.signUpObserver(binding.nickNameEditText.text.toString(), signUpResponse.email, signUpResponse.password)
        signUpViewModel.signUpServiceLiveData.observe(this, Observer { response ->
            if (response?.success == true) {    // 성공
                Log.d("TAG", "signUp: signup success")
                setUserInfo(signUpResponse)
            } else {
                when(response?.message) {
                    "중복된 이메일 입니다." -> {
                        Toast.makeText(this, "이미 사용중인 이메일입니다.", Toast.LENGTH_SHORT).show()
                    }
                    "email : 학교계정을 입력해주세요" -> {
                        Toast.makeText(this, "이메일이 학교계정이 아닙니다.", Toast.LENGTH_SHORT).show()
                    }
                    "password : 크기가 5에서 20 사이여야 합니다" -> {
                        Toast.makeText(this, "비밀번호 길이가 5~20 이어야 합니다.", Toast.LENGTH_SHORT).show()
                    }
                    "email : 학교계정을 입력해주세요, password : 크기가 5에서 20 사이여야 합니다" -> {
                        Toast.makeText(this, "이메일이 학교계정이 아닙니다.", Toast.LENGTH_SHORT).show()
                        Toast.makeText(this, "비밀번호 길이가 5~20 이어야 합니다.", Toast.LENGTH_SHORT).show()
                    }
                }
                Log.d("TAG", "signUp: signup fail, ${response?.message}")
            }
        })
    }

    private fun setUserInfo(signUpResponse: SignUpRequest) {
        userInfoViewModel.setUserInfo(signUpResponse.email, binding.nickNameEditText.text.toString(), binding.githubEditText.text.toString(), binding.discordEditText.text.toString())
        userInfoViewModel.userInfoLiveData.observe(this, Observer { response ->
            if (response?.success == true) {
                startActivity(Intent(this, SelectMajorActivity::class.java)
                    .putExtra("email", signUpResponse.email))
            } else {
                if (response?.status == "404") {
                    Toast.makeText(this, "계정을 찾을 수 없습니다.", Toast.LENGTH_SHORT).show()
                }
                Log.d("TAG", "setUserInfo: ${response?.message}")
            }
        })
    }
}