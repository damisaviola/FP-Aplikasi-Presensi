package com.example.login.View.History

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.login.Model.Model_presensi
import com.example.login.R
import itemAdapter

class history_item : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: itemAdapter
    private lateinit var presensiList: MutableList<Model_presensi>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        recyclerView = findViewById(R.id.rv_itemdata)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Mengisi presensiList dengan data
        presensiList = mutableListOf()
        // Isi presensiList dengan data dari mana pun Anda mendapatkannya

        adapter = itemAdapter(presensiList)
        recyclerView.adapter = adapter

    }
}
