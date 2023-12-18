package com.example.login.Model

import java.io.Serializable

data class Model_presensi(
    var id: Int,
    val nama: String,
    val waktuAbsen: String,
    val lokasi: String,
    val keterangan: String
) : Serializable
