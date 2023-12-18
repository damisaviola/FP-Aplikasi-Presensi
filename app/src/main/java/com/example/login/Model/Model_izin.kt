package com.example.login.Model

import java.io.Serializable

data class Model_izin(
    var id: Int,
    val nama: String,
    val waktuAbsen: String,
    val keterangan: String
) : Serializable