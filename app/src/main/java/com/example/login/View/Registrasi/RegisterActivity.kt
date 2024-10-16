package com.example.login.View.Registrasi

import DatabaseHelper
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.login.Model.Model_registrasi
import com.example.login.R
import com.example.login.View.Login.LoginActivity

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btnLoginListener()

        val etName = findViewById<EditText>(R.id.edt_fullname)
        val etEmail = findViewById<EditText>(R.id.edt_email)
        val etPhone = findViewById<EditText>(R.id.edt_nohp)
        val etPassword = findViewById<EditText>(R.id.edt_password)

        val btnRegister = findViewById<Button>(R.id.R_btn_1)
        btnRegister.setOnClickListener {
            addRecord(etName, etEmail, etPhone, etPassword)
        }
    }

    private fun addRecord(
        etName: EditText,
        etEmail: EditText,
        etPhone: EditText,
        etPassword: EditText
    ) {
        val name = etName.text.toString()
        val email = etEmail.text.toString()
        val phone = etPhone.text.toString()
        val password = etPassword.text.toString()

        val databaseHandler = DatabaseHelper(this)

        if (name.isNotEmpty() && email.isNotEmpty() && phone.isNotEmpty() && password.isNotEmpty()) {
            val status = databaseHandler.addEmployee(Model_registrasi(0, name, email, phone, password))
            if (status > -1) {
                Toast.makeText(applicationContext, "Record saved", Toast.LENGTH_LONG).show()
                etName.text.clear()
                etEmail.text.clear()
                etPhone.text.clear()
                etPassword.text.clear()
            }
        } else {
            Toast.makeText(applicationContext, "Fields cannot be blank", Toast.LENGTH_LONG).show()
        }
    }

    private fun btnLoginListener() {
        val txt_login = findViewById<TextView>(R.id.txt_login)
        txt_login.setOnClickListener {
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
        }
    }
}

