package com.c241ps220.centingapps.data.retrofit.UserRegisterAuth

import com.google.gson.annotations.SerializedName

data class UserRegisterRequest(
    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    val password: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("role")
    val role: String
)