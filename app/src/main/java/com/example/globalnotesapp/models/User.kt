package com.example.globalnotesapp.models

data class User (
    val uid: String = "",
    val name: String = "",
    val age: String = "",
    val email: String = "",
    val posts: ArrayList<String> = ArrayList()
        )