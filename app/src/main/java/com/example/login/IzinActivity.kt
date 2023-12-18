package com.example.login

import DatabaseHelper
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.login.databinding.ActivityIzinBinding
import java.text.SimpleDateFormat
import java.util.Date

class IzinActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIzinBinding
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIzinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DatabaseHelper(this)

        val currentDateAndTime = getCurrentDateTime()
        binding.edtPassword2.setText(formatDateTime(currentDateAndTime))

        binding.btn1.setOnClickListener {
            simpanDataIzin()
        }
    }

    private fun getCurrentDateTime(): Date {
        return Date()
    }

    private fun formatDateTime(date: Date): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return dateFormat.format(date)
    }

    private fun simpanDataIzin() {
        val nama = binding.edtNama.text.toString().trim()
        val tanggalWaktu = binding.edtPassword2.text.toString().trim()
        val keterangan = binding.edtKeterangan.text.toString().trim()

        if (nama.isNotEmpty() && tanggalWaktu.isNotEmpty() && keterangan.isNotEmpty()) {
            val success = dbHelper.insertIzin(nama, tanggalWaktu, keterangan)
            if (success != -1L) {
                Toast.makeText(this, "Data izin berhasil disimpan", Toast.LENGTH_SHORT).show()

                // Navigasi ke HistoryIzinActivity setelah izin berhasil disimpan
                val intent = Intent(this, HistoryIzinActivity::class.java)
                startActivity(intent)

            } else {
                Toast.makeText(this, "Gagal menyimpan data izin", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Harap lengkapi semua field", Toast.LENGTH_SHORT).show()
        }
    }
}