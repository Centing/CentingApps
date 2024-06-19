package com.c241ps220.centingapps.data.retrofit.UserUpdateProfileAuth

import com.google.gson.annotations.SerializedName

data class UserUpdateProfileRequest(
    @SerializedName("id_user")
    val id_user: String,

    @SerializedName("phone")
    val phone: String,

    @SerializedName("address")
    val address: String,

    @SerializedName("gender")
    val gender: String,

    @SerializedName("birth_date")
    val birth_date: String
)