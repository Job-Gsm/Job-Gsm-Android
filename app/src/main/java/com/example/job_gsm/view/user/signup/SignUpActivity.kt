package com.example.job_gsm.view.user.signup

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
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
import com.example.job_gsm.model.data.request.SignUpRequest
import com.example.job_gsm.viewmodel.CheckEmailViewModel
import com.example.job_gsm.viewmodel.SignUpEmailViewModel
import com.example.job_gsm.viewmodel.SignUpViewModel

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private val signUpEmailViewModel by viewModels<SignUpEmailViewModel>()
    private val checkEmailViewModel by viewModels<CheckEmailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val checkEmail = binding.checkCertEmailText
        val checkPw = binding.checkCertPwText
        val checkPwCheck = binding.checkCertPwCheckText
        binding.signInFinishBtn.setOnClickListener {
            checkEmail.visibility = View.INVISIBLE
            checkPw.visibility = View.INVISIBLE
            checkPwCheck.visibility = View.INVISIBLE

            if (binding.signInPw.text.toString() != binding.signInPwCheck.text.toString()) {
                checkPwCheck.visibility = View.VISIBLE
                checkPwCheck.text = "비밀번호가 일치하지 않습니다."
            } else {
                toGetUserInfo()
            }
        }

        binding.certSignInEmailBtn.setOnClickListener {
            if (binding.signInEmail.text!!.isEmpty()) {
                checkEmail.visibility = View.VISIBLE
                checkEmail.text = "필수 입력 항목입니다."
                Log.d("TAG", "onCreate: is empty")
            } else {
                signUpEmailViewModel.signUpSendEmail(binding.signInEmail.text.toString())
                signUpEmailViewModel.signUpEmailServiceLiveData.observe(this, Observer {
                    Log.d("TAG", "onCreate: $it")
                    if (it?.message == "email : 학교계정을 입력해주세요") {
                        Log.d("TAG", "onCreate: not school email")
                        checkEmail.visibility = View.VISIBLE
                        checkEmail.text = "학교계정을 입력해주세요"
                    } else {
                        // 이메일을 보냄
                        sendEmail()
                    }
                })
            }
        }
    }

    private fun toGetUserInfo() {
        if (binding.checkCertEmailText.text.toString() == "인증이 완료되었습니다.") {
            startActivity(Intent(this, GetUserInfoActivity::class.java)
                .putExtra("user", SignUpRequest("", binding.signInEmail.text.toString(), binding.signInPw.text.toString())))
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
                if (response?.status == "400") {
                    Log.d("TAG", "sendEmail: 인증 실패")
                    Toast.makeText(this, "인증번호 5자리를 입력해주세요.", Toast.LENGTH_SHORT).show()
                } else if (response?.success == true) {
                    // 인증 완료시 나오는 텍스트들 잘 나오나 테스트
                    Log.d("TAG", "sendEmail: 인증 완료")
                    Toast.makeText(this, "인증이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                    binding.checkCertEmailText.visibility = View.VISIBLE
                    binding.checkCertEmailText.text = "인증이 완료되었습니다."
                }
            })
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