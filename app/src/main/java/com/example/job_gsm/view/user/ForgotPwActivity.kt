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
import com.example.job_gsm.viewmodel.ForgetPwViewModel

class ForgotPwActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgotPwBinding
    private val viewModel by viewModels<ForgetPwViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityForgotPwBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.emailCertificationBtn.setOnClickListener {
            binding.emailErrorText.visibility = View.GONE
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

                    val number = dialogBinding.certNumber1.text.toString() + dialogBinding.certNumber2.text.toString() +
                            dialogBinding.certNumber3.text.toString() + dialogBinding.certNumber4.text.toString() +
                            dialogBinding.certNumber5.text.toString()

                    dialogBinding.certificationBtn.setOnClickListener {
//                        if (number == response.result.toString()) { // 인증번호가 같다.
//                            Toast.makeText(this, "인증이 완료되었습니다.", Toast.LENGTH_SHORT).show()
//                            binding.newPwLinear.visibility = View.VISIBLE
//                        } else {    // 다르다.
//                            binding.emailErrorText.text = "인증번호가 다릅니다."
//                        }
                    }
                }
                false -> {
                    if (response.status == 404) {
                        binding.emailErrorText.visibility = View.VISIBLE
                        binding.emailErrorText.text = "계정을 찾을 수 없습니다."
                    } else if (response.status == 400) {
                        binding.emailErrorText.visibility = View.VISIBLE
                        binding.emailErrorText.text = "학교계정을 입력해주세요."
                    }
                }
                else -> { return@Observer }
            }
        })
    }
}