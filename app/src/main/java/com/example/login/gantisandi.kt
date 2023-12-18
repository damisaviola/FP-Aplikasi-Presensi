package com.example.login

import DatabaseHelper
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.login.View.Login.LoginActivity

class gantisandi: AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var oldPasswordEditText: EditText
    private lateinit var newPasswordEditText: EditText
    private lateinit var changePasswordButton: Button
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gantisandi)

        dbHelper = DatabaseHelper(this)

        emailEditText = findViewById(R.id.email)
        oldPasswordEditText = findViewById(R.id.edt_old)
        newPasswordEditText = findViewById(R.id.edt_password1)
        changePasswordButton = findViewById(R.id.L_btn_1)

        changePasswordButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val oldPassword = oldPasswordEditText.text.toString().trim()
            val newPassword = newPasswordEditText.text.toString().trim()

            if (email.isNotEmpty() && oldPassword.isNotEmpty() && newPassword.isNotEmpty()) {
                val isPasswordChanged = dbHelper.changePassword(email, oldPassword, newPassword)

                if (isPasswordChanged) {
                    Toast.makeText(this, "Sandi berhasil diubah", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Gagal mengubah sandi", Toast.LENGTH_SHORT).show()

                }
            } else {
                Toast.makeText(this, "Isi semua kolom", Toast.LENGTH_SHORT).show()

            }
        }
    }
}
