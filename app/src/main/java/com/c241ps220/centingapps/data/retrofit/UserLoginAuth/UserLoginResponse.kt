package com.c241ps220.centingapps.data.retrofit.UserLoginAuth

import com.google.gson.annotations.SerializedName

data class UserLoginResponse(
    @SerializedName("email")
    val email: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("phone")
    val phone: String?,

    @SerializedName("address")
    val address: String?
)