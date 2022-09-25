package com.example.job_gsm.view.user.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.job_gsm.databinding.ActivitySelectMajorBinding
import com.example.job_gsm.view.user.SignInActivity
import com.example.job_gsm.viewmodel.user.SelectMajorViewModel
import kotlin.properties.Delegates

class SelectMajorActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySelectMajorBinding

    private val selectMajorViewModel by lazy {
        ViewModelProvider(this, SelectMajorViewModel.Factory(application, email, major, career))[SelectMajorViewModel::class.java] }

    private lateinit var email: String
    private lateinit var major: String
    private var career by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySelectMajorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.setMajorBtn.setOnClickListener {
            email = intent.getStringExtra("email").toString()
            major = binding.selectMajorSpinner.selectedItem.toString()
            career = binding.setCareerEditText.text.toString().toInt()

            if (binding.selectMajorSpinner.selectedItem == "전공 선택") {
                binding.setMajorErrorText.text = "전공을 선택해주세요."
            }
            if (binding.setCareerEditText.text.isEmpty()) {
                binding.setCareerErrorText.text = "필수 입력 항목입니다."
            }

            setMajor()
        }
    }

    private fun setMajor() {
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