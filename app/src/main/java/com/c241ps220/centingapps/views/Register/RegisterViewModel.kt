package com.c241ps220.centingapps.views.Register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.c241ps220.centingapps.Repository.UserRepository
import com.c241ps220.centingapps.data.pref.RegisterResponse
import kotlinx.coroutines.launch

class RegisterViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _registerResult = MutableLiveData<RegisterResponse>()
    val registerResult: LiveData<RegisterResponse> = _registerResult

    fun register(name: String, email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = userRepository.register(name, email, password)
                _registerResult.postValue(response)
            } catch (e: Exception) {
                _registerResult.postValue(RegisterResponse(error = true, message = e.message ?: "Unknown error"))
            }
        }
    }
}