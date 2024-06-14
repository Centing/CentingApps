package com.c241ps220.centingapps.ViewModelFactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.c241ps220.centingapps.views.AnakSection.AddAnak.AddAnakViewModel
import com.c241ps220.centingapps.views.AnakSection.DetailAnak.DetailAnakViewModel
import com.c241ps220.centingapps.views.AnakSection.ListAnak.ListAnakViewModel
import com.c241ps220.centingapps.views.Deteksi.Result.ByUser.ResultDetectByUserViewModel
import com.c241ps220.centingapps.views.Deteksi.SelectChild.SelectAnakViewModel
import com.c241ps220.centingapps.views.Fragment.HistoryFragment.HistoryViewModel
import com.c241ps220.centingapps.views.History.HistoryActivityViewModel

class ViewModelFactory (private val mApplication: Application) : ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(application: Application): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(application)
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
            return HistoryViewModel(mApplication) as T
        }
        if (modelClass.isAssignableFrom(ResultDetectByUserViewModel::class.java)) {
            return ResultDetectByUserViewModel(mApplication) as T
        }
        if (modelClass.isAssignableFrom(HistoryActivityViewModel::class.java)) {
            return HistoryActivityViewModel(mApplication) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}