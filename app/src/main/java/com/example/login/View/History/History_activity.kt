package com.example.login.View.History

import DatabaseHelper
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.login.Model.Model_presensi

import com.example.login.R
import itemAdapter


class History_activity : AppCompatActivity() {

    private lateinit var adapter: itemAdapter
    private lateinit var presensiList: MutableList<Model_presensi>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        // Get data presensi from DatabaseHelper as MutableList
        presensiList = DatabaseHelper(this).showPresensi()

        // Get the RecyclerView from activity_history layout
        val recyclerView = findViewById<RecyclerView>(R.id.rv_itemdata)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Set adapter to RecyclerView
        adapter = itemAdapter(presensiList)
        recyclerView.adapter = adapter
    }
}


