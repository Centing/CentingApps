package com.c241ps220.centingapps.data.retrofit.UserAuth

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("email")
    val email: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("phone")
    val phone: String?,

    @SerializedName("address")
    val address: String?
)