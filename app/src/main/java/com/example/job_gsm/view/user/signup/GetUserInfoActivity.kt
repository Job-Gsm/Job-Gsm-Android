package com.example.job_gsm.view.user.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.job_gsm.databinding.ActivityGetUserInfoBinding
import com.example.job_gsm.viewmodel.user.SignUpViewModel
import com.example.job_gsm.viewmodel.user.UserInfoViewModel

class GetUserInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGetUserInfoBinding

    private val signUpViewModel by lazy {
        ViewModelProvider(this, SignUpViewModel.Factory(application, username, email, pw))[SignUpViewModel::class.java] }
    private val userInfoViewModel by lazy {
        ViewModelProvider(this, UserInfoViewModel.Factory(application, email, username, github, discord))[UserInfoViewModel::class.java]
    }

    private lateinit var username: String
    private lateinit var email: String
    private lateinit var pw: String
    private lateinit var github: String
    private lateinit var discord: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGetUserInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val errorDiscord = binding.errorDiscordText
        val errorGithub = binding.errorGithubText
        val errorUsername = binding.errorNickNameText
        binding.setUserInfoDoneBtn.setOnClickListener {
            errorDiscord.visibility = View.INVISIBLE
            errorGithub.visibility = View.INVISIBLE
            errorUsername.visibility = View.INVISIBLE

            username = binding.nickNameEditText.text.toString()
            email = intent.getStringExtra("email").toString()
            pw = intent.getStringExtra("pw").toString()
            github = binding.githubEditText.text.toString()
            discord = binding.discordEditText.text.toString()

            if (username.isEmpty()) {
                errorUsername.visibility = View.VISIBLE
                errorUsername.text = "필수 입력 항목입니다."
                return@setOnClickListener
            }

            if (discord.isEmpty()) {
                errorDiscord.visibility = View.VISIBLE
                errorDiscord.text = "필수 입력 항목입니다."
                return@setOnClickListener
            }

            if (github.isEmpty()) {
                errorGithub.visibility = View.VISIBLE
                errorGithub.text = "필수 입력 항목입니다."
                return@setOnClickListener
            }

            signUp()
        }
    }

    private fun signUp() {
        signUpViewModel.signUpServiceLiveData.observe(this, Observer { response ->
            if (response?.success == true) {    // 성공
                Log.d("TAG", "signUp: signup success")
                setUserInfo()
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

    private fun setUserInfo() {
        userInfoViewModel.userInfoLiveData.observe(this, Observer { response ->
            if (response?.success == true) {
                startActivity(Intent(this, SelectMajorActivity::class.java)
                    .putExtra("email", email))
            } else {
                if (response?.status == "404") {
                    Toast.makeText(this, "계정을 찾을 수 없습니다.", Toast.LENGTH_SHORT).show()
                }
                Log.d("TAG", "setUserInfo: ${response?.message}")
            }
        })
    }
}