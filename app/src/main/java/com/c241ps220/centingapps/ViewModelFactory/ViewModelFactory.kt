package com.c241ps220.centingapps.ViewModelFactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.c241ps220.centingapps.MainActivityViewModel
import com.c241ps220.centingapps.Repository.UserRepository
import com.c241ps220.centingapps.data.pref.UserPreference
import com.c241ps220.centingapps.views.AnakSection.AddAnak.AddAnakViewModel
import com.c241ps220.centingapps.views.AnakSection.DetailAnak.DetailAnakViewModel
import com.c241ps220.centingapps.views.AnakSection.ListAnak.ListAnakViewModel
import com.c241ps220.centingapps.views.Deteksi.Result.ByUser.ResultDetectByUserViewModel
import com.c241ps220.centingapps.views.Deteksi.SelectChild.SelectAnakViewModel
import com.c241ps220.centingapps.views.Fragment.BerandaFragment.BerandaViewModel
import com.c241ps220.centingapps.views.Fragment.HistoryFragment.HistoryViewModel
import com.c241ps220.centingapps.views.Fragment.ProfileFragment.ProfileViewModel
import com.c241ps220.centingapps.views.History.HistoryActivityViewModel
import com.c241ps220.centingapps.views.Login.LoginViewModel
import com.c241ps220.centingapps.views.SplashScreen.SplashscreenViewModel

class ViewModelFactory(private val mApplication: Application, private val pref: UserPreference) : ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(application: Application, pref: UserPreference): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(application, pref)
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SelectAnakViewModel::class.java)) {
            return SelectAnakViewModel(mApplication) as T
        }
        if (modelClass.isAssignableFrom(AddAnakViewModel::class.java)) {
            return AddAnakViewModel(mApplication) as T
        }
        if (modelClass.isAssignableFrom(ListAnakViewModel::class.java)) {
            return ListAnakViewModel(mApplication) as T
        }
        if (modelClass.isAssignableFrom(DetailAnakViewModel::class.java)) {
            return DetailAnakViewModel(mApplication) as T
        }
        if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
            return HistoryViewModel(mApplication, pref) as T
        }
        if (modelClass.isAssignableFrom(ResultDetectByUserViewModel::class.java)) {
            return ResultDetectByUserViewModel(mApplication) as T
        }
        if (modelClass.isAssignableFrom(HistoryActivityViewModel::class.java)) {
            return HistoryActivityViewModel(mApplication) as T
        }
        if (modelClass.isAssignableFrom(SplashscreenViewModel::class.java)) {
            return SplashscreenViewModel(pref) as T
        }
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(pref) as T
        }
        if (modelClass.isAssignableFrom(BerandaViewModel::class.java)) {
            return BerandaViewModel(pref) as T
        }
        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            return MainActivityViewModel(pref) as T
        }
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(mApplication, pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}