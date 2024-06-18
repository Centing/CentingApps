package com.c241ps220.centingapps.data.pref

data class UserModel(
    val name: String,
    val email: String,
    val gender: String,
    val birthDate: String,
    val isLogin: Boolean = false
)