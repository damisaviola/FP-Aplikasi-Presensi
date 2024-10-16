package com.example.login

import DatabaseHelper
import android.app.Dialog
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.login.Model.Model_izin
import izinAdapter

class HistoryIzinActivity : AppCompatActivity(), izinAdapter.OnItemClickListener {


    private lateinit var adapter: izinAdapter
    private lateinit var presensiList: MutableList<Model_izin>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_izin)

        presensiList = DatabaseHelper(this).showIzin()

        val recyclerView = findViewById<RecyclerView>(R.id.rv_itemdata)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = izinAdapter(this, presensiList, this)
        recyclerView.adapter = adapter
    }

    override fun onEditClick(position: Int) {
        val editedItem = presensiList[position]
        val dialog = showEditDialog(editedItem)
        dialog?.let {
            it.show()
        }
    }


    private fun showEditDialog(editedItem: Model_izin): Dialog {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.activity_edit)

        val etEditedNama = dialog.findViewById<EditText>(R.id.etEditedNama)
        val etEditedKeterangan = dialog.findViewById<EditText>(R.id.etEditedKet)
        val btnSave = dialog.findViewById<Button>(R.id.btnsave)

        etEditedNama.setText(editedItem.nama)
        etEditedKeterangan.setText(editedItem.keterangan)

        btnSave.setOnClickListener {
            val namaBaru = etEditedNama.text.toString()
            val keteranganBaru = etEditedKeterangan.text.toString()

            val updatedItem =
                Model_izin(editedItem.id, namaBaru, editedItem.waktuAbsen, keteranganBaru)
            onUpdatedItem(updatedItem)

            dialog.dismiss()
        }

        return dialog
    }


    override fun onUpdatedItem(updatedItem: Model_izin) {
        val dbHelper = DatabaseHelper(this)
        val isUpdated = dbHelper.updateIzin(updatedItem)

        if (isUpdated) {
            Toast.makeText(this, "Item updated successfully", Toast.LENGTH_SHORT).show()

            presensiList.clear()
            presensiList.addAll(dbHelper.showIzin())
            adapter.notifyDataSetChanged()
        } else {
            Toast.makeText(this, "Failed to update item", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onDeleteClick(position: Int) {
        if (position in 0 until presensiList.size) {
            val deletedItem = presensiList[position]
            val dbHelper = DatabaseHelper(this)
            val isDeleted = dbHelper.deleteIzin(deletedItem)

            if (isDeleted) {
                presensiList.removeAt(position)
                adapter.notifyItemRemoved(position)
                Toast.makeText(this, "Item deleted successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Failed to delete item", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Invalid item position", Toast.LENGTH_SHORT).show()
        }
    }
}