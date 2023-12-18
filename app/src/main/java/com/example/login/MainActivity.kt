package com.example.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.login.View.Login.LoginActivity
import com.example.login.View.Registrasi.RegisterActivity
import com.example.login.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        btnLoginListener()
        btn2LoginListener()
    }

    private fun btnLoginListener() {
        binding.btn1.setOnClickListener {
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))

        }
    }

    private fun btn2LoginListener() {
        binding.btn2.setOnClickListener {
            startActivity(Intent(this@MainActivity, RegisterActivity::class.java))

        }
    }


}
