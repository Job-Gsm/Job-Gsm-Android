package com.example.job_gsm.view.user.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.job_gsm.databinding.ActivitySelectMajorBinding
import com.example.job_gsm.view.user.SignInActivity
import com.example.job_gsm.viewmodel.user.SelectMajorViewModel

class SelectMajorActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySelectMajorBinding
    val selectMajorViewModel by viewModels<SelectMajorViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySelectMajorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val email = intent.getStringExtra("email")
        Log.d("TAG", "onCreate email: $email")
        binding.setMajorBtn.setOnClickListener {
            if (binding.selectMajorSpinner.selectedItem == "전공 선택") {
                binding.setMajorErrorText.text = "전공을 선택해주세요."
            }
            if (binding.setCareerEditText.text.isEmpty()) {
                binding.setCareerErrorText.text = "필수 입력 항목입니다."
            }

            setMajor(email)
        }
    }

    private fun setMajor(email: String?) {
        selectMajorViewModel.setMajor(email!!, binding.selectMajorSpinner.selectedItem.toString(), binding.setCareerEditText.text.toString().toInt())
        selectMajorViewModel.selectMajorLiveData.observe(this, Observer { response ->
            if (response?.success == true) {
                Toast.makeText(this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, SignInActivity::class.java))
            } else {
                if (response?.status == "404") {
                    Toast.makeText(this, "계정을 찾을 수 없습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}