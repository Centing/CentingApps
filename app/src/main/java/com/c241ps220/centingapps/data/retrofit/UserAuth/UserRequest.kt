package com.c241ps220.centingapps.data.retrofit.UserAuth

import com.google.gson.annotations.SerializedName

data class UserRequest(
    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    val password: String
)