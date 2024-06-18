package com.c241ps220.centingapps.data.retrofit

import com.c241ps220.centingapps.data.pref.LoginResponse
import com.c241ps220.centingapps.data.pref.RegisterResponse
import com.c241ps220.centingapps.data.retrofit.UserLoginAuth.UserLoginRequest
import com.c241ps220.centingapps.data.retrofit.UserLoginAuth.UserLoginResponse
import retrofit2.Call

import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @POST("auth/login")
    fun loginUser(@Body userLoginRequest: UserLoginRequest): Call<UserLoginResponse>
}
