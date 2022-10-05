package com.example.job_gsm.view.user

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.job_gsm.databinding.ActivityForgotPwBinding
import com.example.job_gsm.databinding.CustomDialogForgetPwSendEmailBinding
import com.example.job_gsm.viewmodel.user.ChangePwViewModel
import com.example.job_gsm.viewmodel.user.CheckEmailViewModel
import com.example.job_gsm.viewmodel.user.SendEmailViewModel

class ForgotPwActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgotPwBinding

    private val changePwViewModel by lazy {     // change password
        ViewModelProvider(this, ChangePwViewModel.Factory(application, email, newPw))[ChangePwViewModel::class.java] }
    private val sendEmailViewModel by lazy {    // send email
        ViewModelProvider(this, SendEmailViewModel.Factory(application, email))[SendEmailViewModel::class.java] }
    private val checkEmailViewModel by lazy {   // check email
        ViewModelProvider(this, CheckEmailViewModel.Factory(application, email, key))[CheckEmailViewModel::class.java] }

    private lateinit var email: String
    private lateinit var newPw: String
    private lateinit var key: String

    private lateinit var emailCheck: TextView
    private lateinit var pwCheck: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityForgotPwBinding.inflate(layoutInflater)
        setContentView(binding.root)

        emailCheck = binding.emailStatusText
        pwCheck = binding.pwStatusText
        binding.emailCertificationBtn.setOnClickListener {
            // 이메일 인증
            emailCheck.visibility = View.INVISIBLE
            pwCheck.visibility = View.INVISIBLE
            binding.pwCheckStatusText.visibility = View.INVISIBLE

            email = binding.forgetEmailEditText.text.toString()
            if (email.isEmpty()) {
                emailCheck.visibility = View.VISIBLE
                emailCheck.text = "필수 입력 항목입니다."
            }

            if (newPw != binding.newPwCheckEditText.text.toString()) {
                binding.pwCheckStatusText.visibility = View.VISIBLE
                binding.pwCheckStatusText.text = "비밀번호가 일치하지 않습니다."
            }

            changePwSendEmail()
        }

        binding.changePwBtn.setOnClickListener {
            // 비밀번호 변경 로직
            changePassword()
        }

        binding.backLoginText.setOnClickListener { finish() }
    }

    private fun changePassword() {
        changePwViewModel.changePwLiveData.observe(this, Observer { response ->
            if (response?.success == true) {
                Toast.makeText(this, "비밀번호가 변경되었습니다.", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                when(response?.message) {
                    "email : 학교계정을 입력해주세요" -> {
                        emailCheck.visibility = View.VISIBLE
                        emailCheck.text = "학교계정을 입력해주세요."
                    }
                    "password : 크기가 5에서 20 사이여야 합니다" -> {
                        pwCheck.visibility = View.VISIBLE
                        pwCheck.text = "비밀번호 길이가 5~20 사이여야 합니다."
                    }
                }
            }
        })
    }

    // send email to change password
    private fun changePwSendEmail() {
        sendEmailViewModel.sendEmailServiceLiveData.observe(this, Observer { sendEmailResponse ->
            if (sendEmailResponse?.success == true) {
                createDialog()
            } else {
                when(sendEmailResponse?.message) {
                    "email : 학교계정을 입력해주세요" -> {
                        emailCheck.visibility = View.VISIBLE
                        emailCheck.text = "학교계정을 입력해주세요."
                    }
                }
            }
        })
    }

    // create email certification dialog
    private fun createDialog() {
        val dialogBinding = CustomDialogForgetPwSendEmailBinding.inflate(layoutInflater)
        val dialogBuilder = AlertDialog.Builder(this)
            .setView(dialogBinding.root)
        val dialog = dialogBuilder.show()
        val mDialog = Dialog(this)
        mDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        setTimer(dialogBinding, dialog)

        dialogBinding.certificationBtn.setOnClickListener {
            key = getNumber(dialogBinding)
            checkEmailViewModel.checkEmailServiceLiveData.observe(this, Observer { response ->
                if (response?.success == true) {
                    Toast.makeText(this, "인증이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                    emailCheck.visibility = View.VISIBLE
                    emailCheck.text = "인증이 완료되었습니다."
                } else {
                    if (response?.status == "400") Toast.makeText(this, "인증번호 5자리를 입력해주세요.", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun setTimer(dialogBinding: CustomDialogForgetPwSendEmailBinding, dialog: AlertDialog) {
        object : CountDownTimer(300000, 1000) {   // 60000 = 1분
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
                Toast.makeText(this@ForgotPwActivity, "인증시간이 만료되었습니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show()
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