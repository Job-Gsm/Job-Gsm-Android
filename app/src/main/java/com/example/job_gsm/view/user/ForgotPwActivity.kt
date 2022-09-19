package com.example.job_gsm.view.user

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.job_gsm.databinding.ActivityForgotPwBinding
import com.example.job_gsm.databinding.CustomDialogForgetPwSendEmailBinding
import com.example.job_gsm.viewmodel.CheckEmailViewModel
import com.example.job_gsm.viewmodel.ForgetPwViewModel

class ForgotPwActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgotPwBinding
    private val viewModel by viewModels<ForgetPwViewModel>()
    private val checkEmailViewModel by viewModels<CheckEmailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityForgotPwBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.emailCertificationBtn.setOnClickListener {
            binding.emailStatusText.visibility = View.GONE
            newPwSendEmail(binding.forgetEmailEditText.text.toString())
        }

        binding.changePwBtn.setOnClickListener {
            // 비밀번호 변경 로직
        }

        binding.backLoginText.setOnClickListener { finish() }
    }

    private fun newPwSendEmail(email: String) {
        viewModel.forgetPw(email)
        viewModel.forgetPwServiceLiveData.observe(this, Observer { response ->
            when(response?.success) {
                true -> {
                    val dialogBinding = CustomDialogForgetPwSendEmailBinding.inflate(layoutInflater)
                    val dialogBuilder = AlertDialog.Builder(this)
                        .setView(dialogBinding.root)
                    val dialog = dialogBuilder.show()
                    val mDialog = Dialog(this)
                    mDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

                    dialogBinding.certificationBtn.setOnClickListener {
                        checkEmailViewModel.checkEmail(getNumber(dialogBinding))
                        checkEmailViewModel.checkEmailServiceLiveData.observe(this, Observer {
                            if (it?.status == "400") {
                                Toast.makeText(this, "인증번호 5자리를 입력해주세요.", Toast.LENGTH_SHORT).show()
                            } else if (it?.success == true) {
                                Toast.makeText(this, "인증이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                                dialog.dismiss()
                                binding.newPwLinear.visibility = View.VISIBLE
                                binding.emailStatusText.text = "인증 완료됨"
                                binding.emailStatusText.setTextColor(Color.GREEN)
                            }
                        })
                    }
                }
                false -> {
                    if (response.status == "404") {
                        binding.emailStatusText.visibility = View.VISIBLE
                        binding.emailStatusText.text = "계정을 찾을 수 없습니다."
                        binding.emailStatusText.setTextColor(Color.RED)
                    } else if (response.status == "400") {
                        binding.emailStatusText.visibility = View.VISIBLE
                        binding.emailStatusText.text = "학교계정을 입력해주세요."
                        binding.emailStatusText.setTextColor(Color.RED)
                    }
                }
                else -> { return@Observer }
            }
        })
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