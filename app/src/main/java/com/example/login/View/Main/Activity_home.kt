package com.example.login.View.Main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.login.HistoryIzinActivity
import com.example.login.IzinActivity
import com.example.login.View.Absen.Dash_activity
import com.example.login.View.History.History_activity
import com.example.login.databinding.ActivityHomeBinding

class Activity_home : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setupClickListeners()
        btnizinListener()

    }

    private fun setupClickListeners() {

        binding.msk.setOnClickListener {
            val intent = Intent(this@Activity_home, Dash_activity::class.java)
            startActivity(intent)
        }

        binding.History.setOnClickListener {
            val intent = Intent(this@Activity_home, History_activity::class.java)
            startActivity(intent)
        }


    }

    private fun btnizinListener() {
        binding.HIzin.setOnClickListener {
            startActivity(Intent(this@Activity_home, HistoryIzinActivity::class.java))
        }

        binding.izin.setOnClickListener {
            startActivity(Intent(this@Activity_home, IzinActivity::class.java))
        }

    }
}
