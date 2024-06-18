package com.c241ps220.centingapps.views.SplashScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.c241ps220.centingapps.Repository.UserRepository
import com.c241ps220.centingapps.data.pref.UserModel
import com.c241ps220.centingapps.data.pref.UserPreference

class SplashscreenViewModel(private val pref: UserPreference) : ViewModel() {
    fun getSession(): LiveData<UserModel> {
        return pref.getSession().asLiveData()
    }
}