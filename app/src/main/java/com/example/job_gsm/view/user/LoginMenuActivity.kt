package com.example.job_gsm.view.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.core.app.ActivityCompat
import com.example.job_gsm.databinding.ActivityLoginMenuBinding

class LoginMenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setStatusBarTranslucent(true);

        binding.loginBtn.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }

        binding.registerBtn.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }

    private fun setStatusBarTranslucent(makeTranslucent: Boolean) {
        if (makeTranslucent) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
}