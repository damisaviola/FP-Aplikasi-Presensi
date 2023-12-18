package com.example.login.View.Absen

import DatabaseHelper
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.login.View.History.History_activity
import com.example.login.databinding.ActivityDashBinding
import java.text.SimpleDateFormat
import java.util.Date

class Dash_activity : AppCompatActivity() {
    private lateinit var binding: ActivityDashBinding
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        dbHelper = DatabaseHelper(this)

        // Mendapatkan nama pengguna dari database
        val loggedInUserName = dbHelper.GetUserName()

        // Mengisi field nama jika nama pengguna ditemukan
        loggedInUserName?.let {
            binding.edtNama.setText(loggedInUserName.toString())
        }

        // Mengatur tanggal dan waktu pada EditText
        val currentDateAndTime = getCurrentDateTime()
        binding.edtPassword2.setText(formatDateTime(currentDateAndTime))

        binding.btn1.setOnClickListener {
            savePresensiDataToDB()
            navigateToHistory()
        }
    }

    private fun navigateToHistory() {
        val intent = Intent(this, History_activity::class.java)
        startActivity(intent)
    }

    private fun getCurrentDateTime(): Date {
        return Date()
    }

    private fun formatDateTime(date: Date): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return dateFormat.format(date)
    }

    private fun savePresensiDataToDB() {
        val namaInput = binding.edtNama.text.toString()
        val tanggalInput = binding.edtPassword2.text.toString()
        val ket = binding.edtKeterangan.text.toString()

        // Simpan data ke dalam database lokal
        dbHelper.insertPresensiData(namaInput, tanggalInput, ket)
    }
}
