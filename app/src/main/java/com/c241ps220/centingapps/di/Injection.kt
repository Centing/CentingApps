package com.c241ps220.centingapps.di

import android.content.Context
import com.c241ps220.centingapps.data.pref.UserPreference
import com.c241ps220.centingapps.data.retrofit.ApiConfig
import com.c241ps220.centingapps.data.retrofit.ApiService

object Injection {
    fun provideUserPreference(context: Context): UserPreference {
        return UserPreference.getInstance(context)
    }

    fun provideApiService(): ApiService {
        return ApiConfig.createApiService()
    }
}