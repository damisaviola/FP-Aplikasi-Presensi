package com.example.login.View.Login

import DatabaseHelper
import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.login.MainActivity
import com.example.login.R
import com.example.login.View.Main.Activity_home
import com.example.login.View.Registrasi.RegisterActivity
import com.example.login.gantisandi

class LoginActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var showPassCheckbox: CheckBox
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btn2LoginListener()
        btnbackListener()
        btnRegListener()
        dbHelper = DatabaseHelper(this)

        emailEditText = findViewById(R.id.edt_email)
        passwordEditText = findViewById(R.id.edt_password)
        loginButton = findViewById(R.id.L_btn_1)
        showPassCheckbox = findViewById(R.id.showPass)

        showPassCheckbox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {

                passwordEditText.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
            } else {

                passwordEditText.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }



        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                val isLoggedIn = dbHelper.userLogin(email, password)
                if (isLoggedIn) {
                    val loggedInUserName = dbHelper.getLoggedInUserName(email)

                    if (!loggedInUserName.isNullOrEmpty()) {
                        val intent = Intent(this, Activity_home::class.java)
                        intent.putExtra("name", loggedInUserName)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Nama pengguna tidak ditemukan", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    Toast.makeText(this, "Email atau password salah", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Isi email dan password", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun btnbackListener() {
        val Lback = findViewById<ImageView>(R.id.Lback)
        Lback.setOnClickListener {
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        }
    }

    private fun btn2LoginListener() {
        val Lpass = findViewById<TextView>(R.id.LPass)
        Lpass.setOnClickListener {
            startActivity(Intent(this@LoginActivity, gantisandi::class.java))
        }
    }

    private fun btnRegListener() {
        val txt_register = findViewById<TextView>(R.id.txt_register)
        txt_register.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }
    }


}
