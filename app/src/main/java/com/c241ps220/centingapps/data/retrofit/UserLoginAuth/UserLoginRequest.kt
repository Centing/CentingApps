package com.c241ps220.centingapps.data.retrofit.UserLoginAuth

import com.google.gson.annotations.SerializedName

data class UserLoginRequest(
    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    val password: String
)