package com.example.job_gsm.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import com.example.job_gsm.R
import com.example.job_gsm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.menuImage.setOnClickListener {
            setPopMenu(it)
        }
    }

    private fun setPopMenu(view: View) {
        val popupMenu = PopupMenu(applicationContext, view)
        menuInflater.inflate(R.menu.main_context_menu, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.profile -> {
                    Toast.makeText(this@MainActivity, "profile", Toast.LENGTH_SHORT).show()
                }
                R.id.notification -> {
                    Toast.makeText(this@MainActivity, "notification", Toast.LENGTH_SHORT).show()
                }
                R.id.option -> {
                    Toast.makeText(this@MainActivity, "option", Toast.LENGTH_SHORT).show()
                }
                R.id.bookmark_list -> {
                    Toast.makeText(this@MainActivity, "bookmark_list", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            false
        }
        popupMenu.show()
    }
}