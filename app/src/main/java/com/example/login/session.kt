package com.example.login

object session {
    private var username: String? = null

    fun setUsername(nama: String) {
        username = nama
    }

    fun getUsername(): String? {
        return username
    }
}