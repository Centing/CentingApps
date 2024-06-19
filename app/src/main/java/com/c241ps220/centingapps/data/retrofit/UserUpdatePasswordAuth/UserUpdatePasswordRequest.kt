package com.c241ps220.centingapps.data.retrofit.UserUpdatePasswordAuth

import com.google.gson.annotations.SerializedName

data class UserUpdatePasswordRequest(
    @SerializedName("id_user")
    val id_user: String,

    @SerializedName("old_password")
    val old_password: String,

    @SerializedName("new_password")
    val new_password: String
)