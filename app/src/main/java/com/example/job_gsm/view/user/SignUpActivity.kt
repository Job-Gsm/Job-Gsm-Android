package com.example.job_gsm.view.user

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.job_gsm.databinding.ActivitySignUpBinding
import com.example.job_gsm.viewmodel.SignUpViewModel

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private val viewModel by viewModels<SignUpViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signInFinishBtn.setOnClickListener {
            binding.emailInputLayout.isErrorEnabled = false
            binding.nicknameInputLayout.isErrorEnabled = false
            binding.pwInputLayout.isErrorEnabled = false
            binding.pwCheckInputLayout.isErrorEnabled = false

            ifEditTextIsEmpty()
        }
    }

    private fun ifEditTextIsEmpty() {
        if (binding.signInNickName.text.toString().isNotEmpty() && binding.signInEmail.text.toString().isNotEmpty() && binding.signInPw.text.toString().isNotEmpty()) {
            signIn(binding.signInNickName.text.toString(), binding.signInEmail.text.toString(), binding.signInPw.text.toString())
        } else if (binding.signInNickName.text.toString().isEmpty() && binding.signInEmail.text.toString().isEmpty() && binding.signInPw.text.toString().isEmpty()) {
            binding.emailInputLayout.error = "필수 입력사항입니다."
            binding.pwInputLayout.error = "필수 입력사항입니다."
            binding.nicknameInputLayout.error = "필수 입력사항입니다."
            return
        } else if (binding.signInEmail.text.toString().isEmpty()) {
            binding.emailInputLayout.error = "필수 입력사항입니다."
            return
        } else if (binding.signInPw.text.toString().isEmpty()) {
            binding.pwInputLayout.error = "필수 입력사항입니다."
            return
        } else if (binding.signInNickName.text.toString().isEmpty()) {
            binding.nicknameInputLayout.error = "필수 입력사항입니다."
            return
        }
    }

    private fun signIn(username: String, email: String, pw: String) {
        val emailInputLay = binding.emailInputLayout
        val pwInputLay = binding.pwInputLayout
        val pwCheckInputLay = binding.pwCheckInputLayout
        val usernameInputLay = binding.nicknameInputLayout

        viewModel.signUpObserver(username, email, pw)
        viewModel.signUpServiceLiveData.observe(this, Observer {
            if (it != null) {
                Log.d("TAG", "signIn: ${it.status}")

                if (pw != binding.signInPwCheck.text.toString()) {
                    pwCheckInputLay.error = "비밀번호가 일치하지 않습니다."
                } else {
                    if (binding.signInPw.text.toString() == binding.signInPwCheck.text.toString()) {
                        when(it.message) {
                            "계정을 찾을 수 없습니다." -> {
                                emailInputLay.error = "존재하지 않는 계정입니다."
                            }
                            "중복된 이메일 입니다." -> {
                                emailInputLay.error = "이미 가입 된 이메일입니다."
                            }
                            "username : 크기가 2에서 5 사이여야 합니다" -> {
                                usernameInputLay.error = "길이가 2에서 5사이여야 합니다."
                            }
                            "email : 학교계정을 입력해주세요" -> {
                                emailInputLay.error = "학교계정을 입력해주세요."
                            }
                            "password : 크기가 10에서 20 사이여야 합니다" -> {
                                pwInputLay.error = "길이가 10에서 20사이여야 합니다."
                            }
                            "email : 학교계정을 입력해주세요, password : 크기가 10에서 20 사이여야 합니다" -> {
                                emailInputLay.error = "학교계정을 입력해주세요."
                                pwInputLay.error = "길이가 10에서 20사이여야 합니다."
                            }
                            "성공" -> {
                                Toast.makeText(this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this, SignInActivity::class.java))
                                finish()
                            }
                        }
                    } else {
                        binding.pwCheckInputLayout.error = "비밀번호가 다릅니다."
                    }
                }
            } else {
                Log.d("FAIL", "signIn else: $it")
            }
        })
    }

}