package com.c241ps220.centingapps.data.retrofit.UserRegisterAuth

import com.google.gson.annotations.SerializedName

data class UserRegisterResponse(
    @SerializedName("message")
    val message: String,

    @SerializedName("userId")
    val userId: String
)