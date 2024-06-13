package com.c241ps220.centingapps.views.Fragment.HistoryFragment

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.c241ps220.centingapps.Repository.ChildRepository
import com.c241ps220.centingapps.data.database.child.Child

class HistoryViewModel(application: Application) : ViewModel() {
    private val mChildRepository: ChildRepository = ChildRepository(application)

    fun getAllChild(): LiveData<List<Child>> = mChildRepository.getAllChild()

}