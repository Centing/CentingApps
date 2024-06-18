package com.c241ps220.centingapps.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.c241ps220.centingapps.data.pref.UserModel
import com.c241ps220.centingapps.data.pref.UserPreference
import com.c241ps220.centingapps.data.retrofit.ApiService

class UserAuthRepository(
    private val userPreference: UserPreference,
    private val apiService: ApiService
) {
    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): LiveData<UserModel> {
        return userPreference.getSession().asLiveData()
    }


    companion object {
        private const val TAG = "UserAuthRepository"

        @Volatile
        private var instance: UserAuthRepository? = null
        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService
        ): UserAuthRepository =
            instance ?: synchronized(this) {
                instance ?: UserAuthRepository(userPreference, apiService)
            }.also { instance = it }
    }
}