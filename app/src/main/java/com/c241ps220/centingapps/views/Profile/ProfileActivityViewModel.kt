package com.c241ps220.centingapps.views.Profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.c241ps220.centingapps.data.pref.UserModel
import com.c241ps220.centingapps.data.pref.UserPreference
import kotlinx.coroutines.launch

class ProfileActivityViewModel(private val pref: UserPreference) : ViewModel() {
    fun getSession(): LiveData<UserModel> {
        return pref.getSession().asLiveData()
    }

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            pref.saveSession(user)
        }
    }
}
