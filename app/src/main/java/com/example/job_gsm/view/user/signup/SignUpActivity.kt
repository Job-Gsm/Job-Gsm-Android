package com.example.job_gsm.view.user.signup

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.job_gsm.databinding.ActivitySignUpBinding
import com.example.job_gsm.databinding.CustomDialogForgetPwSendEmailBinding
import com.example.job_gsm.viewmodel.CheckEmailViewModel
import com.example.job_gsm.viewmodel.SignUpEmailViewModel
import com.example.job_gsm.viewmodel.SignUpViewModel

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private val viewModel by viewModels<SignUpViewModel>()
    private val signUpEmailViewModel by viewModels<SignUpEmailViewModel>()
    private val checkEmailViewModel by viewModels<CheckEmailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signInFinishBtn.setOnClickListener {
            binding.nicknameInputLayout.error = ""
            binding.pwInputLayout.error = ""
            binding.pwCheckInputLayout.error = ""

            signUp()
        }

        binding.certSignInEmailBtn.setOnClickListener {
            if (binding.signInEmail.text!!.isEmpty()) {
                binding.emailInputLayout.error = "필수 입력 항목입니다."
            } else {
                signUpEmailViewModel.signUpSendEmail(binding.signInEmail.text.toString())
                signUpEmailViewModel.signUpEmailServiceLiveData.observe(this, Observer {
                    if (it?.message == "email : 학교계정을 입력해주세요") {
                        binding.emailInputLayout.error = "학교계정을 입력해주세요"
                    } else {
                        // 이메일을 보냄
                        sendEmail()
                    }
                })
            }
        }
    }

    private fun signUp() {
        if (binding.checkCertEmailText.visibility == View.GONE) {
            Log.d("TAG", "signUp: 잘됨")
        }
    }

    // create custom dialog
    private fun sendEmail() {
        val dialogBinding = CustomDialogForgetPwSendEmailBinding.inflate(layoutInflater)
        val dialogBuilder = AlertDialog.Builder(this)
            .setView(dialogBinding.root)
        val dialog = dialogBuilder.show()
        val mDialog = Dialog(this)
        mDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        // 비밀번호 유효기간 5분 알아서 설정하기
        setTimer(dialogBinding, dialog)

        // 인증번호 인증하기
        dialogBinding.certificationBtn.setOnClickListener {
            checkEmailViewModel.checkEmail(getNumber(dialogBinding))
            checkEmailViewModel.checkEmailServiceLiveData.observe(this, Observer { response ->
                if (response?.status == 400) {
                    Log.d("TAG", "sendEmail: 인증 실패")
                    Toast.makeText(this, "인증번호 5자리를 입력해주세요.", Toast.LENGTH_SHORT).show()
                } else if (response?.success == true) {
                    // 인증 완료시 나오는 텍스트들 잘 낭나 테스트
                    Log.d("TAG", "sendEmail: 인증 완료")
                    Toast.makeText(this, "인증이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                    binding.emailInputLayout.isErrorEnabled = false
                    binding.checkCertEmailText.visibility = View.VISIBLE
                    dialog.dismiss()
                }
            })

            Toast.makeText(this, "인증이 완료되었습니다.", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
    }

    private fun setTimer(dialogBinding: CustomDialogForgetPwSendEmailBinding, dialog: AlertDialog) {
        object :CountDownTimer(300000, 1000) {   // 60000 = 1분
            override fun onTick(time: Long) {
                val minute = time / 60000       // 분
                val sec = time % 60000 / 1000   // 초

                // 시간 설정
                dialogBinding.certTimeMinute.text = minute.toString()
                if (sec < 10) {
                    dialogBinding.certTomeSecond.text = "0$sec"
                } else {
                    dialogBinding.certTomeSecond.text = sec.toString()
                }
            }

            // 종료 로직
            override fun onFinish() {
                Log.d("Timer", "onFinish: finish")
                Toast.makeText(this@SignUpActivity, "인증시간이 만료되었습니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
        }.start()
    }

    private fun getNumber(dialogBinding: CustomDialogForgetPwSendEmailBinding): String {
        val num1 = dialogBinding.certNumber1.text.toString()
        val num2 = dialogBinding.certNumber2.text.toString()
        val num3 = dialogBinding.certNumber3.text.toString()
        val num4 = dialogBinding.certNumber4.text.toString()
        val num5 = dialogBinding.certNumber5.text.toString()

        return num1+num2+num3+num4+num5
    }
}