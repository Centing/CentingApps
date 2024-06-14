package com.c241ps220.centingapps.ViewModelFactory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.c241ps220.centingapps.Repository.UserRepository
import com.c241ps220.centingapps.di.Injection
import com.c241ps220.centingapps.views.Login.LoginViewModel
import com.c241ps220.centingapps.views.Register.RegisterViewModel

class UserViewModelFactory (private val userRepository: UserRepository
) : ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTANCE: UserViewModelFactory? = null
        @JvmStatic
        fun getInstance(context: Context): UserViewModelFactory {
            if (INSTANCE == null) {
                synchronized(UserViewModelFactory::class.java) {
                    val userPreference = Injection.provideUserPreference(context)
                    val apiService = Injection.provideApiService()
                    INSTANCE = UserViewModelFactory(
                        UserRepository.getInstance(userPreference, apiService),
                    )
                }
            }
            return INSTANCE as UserViewModelFactory
        }
    }
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(userRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}