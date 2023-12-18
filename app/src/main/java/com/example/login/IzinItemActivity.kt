package com.example.login

import DatabaseHelper
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.login.Model.Model_izin
import izinAdapter

class IzinItemActivity : AppCompatActivity(), izinAdapter.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: izinAdapter
    private lateinit var izinList: MutableList<Model_izin>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_izin)

        recyclerView = findViewById(R.id.rv_itemdata)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Mengisi izinList dengan data perizinan dari DatabaseHelper
        izinList = getDataIzin()

        adapter = izinAdapter(this, izinList, this) // Menggunakan 'this' sebagai Context
        recyclerView.adapter = adapter
}

        // Fungsi untuk mendapatkan data perizinan dari DatabaseHelper
    private fun getDataIzin(): MutableList<Model_izin> {
        return DatabaseHelper(this).showIzin()
    }

    override fun onEditClick(position: Int) {
        val editedItem = izinList[position]
        val intent = Intent(this, edit_activity::class.java)
        intent.putExtra("EDITED_ITEM", editedItem)
        startActivity(intent)
    }


    override fun onDeleteClick(position: Int) {
        val deletedItem = izinList[position]
        val dbHelper = DatabaseHelper(this)
        val isDeleted = dbHelper.deleteIzin(deletedItem)

        if (isDeleted) {
            izinList.removeAt(position)
            adapter.notifyItemRemoved(position)
            Toast.makeText(this, "Item deleted successfully", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Failed to delete item", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onUpdatedItem(updatedItem: Model_izin) {
        // Menyimpan data yang diperbarui ke database
        val dbHelper = DatabaseHelper(this)
        val isUpdated = dbHelper.updateIzin(updatedItem)

        if (isUpdated) {
            // Tampilkan pesan bahwa data berhasil diperbarui
            // Misalnya: Toast.makeText(this, "Item updated successfully", Toast.LENGTH_SHORT).show()
        } else {
            // Tampilkan pesan bahwa gagal memperbarui data
            // Misalnya: Toast.makeText(this, "Failed to update item", Toast.LENGTH_SHORT).show()
        }
    }
}
