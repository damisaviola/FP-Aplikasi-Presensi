package com.example.login.Model

class user {
    data class User(
        val id: Int,
        val name: String,
        val email: String,
        val phone: String,
        val password: String
    )
}