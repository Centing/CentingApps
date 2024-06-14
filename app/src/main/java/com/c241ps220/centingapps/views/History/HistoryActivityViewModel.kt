package com.c241ps220.centingapps.views.History

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.c241ps220.centingapps.Repository.ChildRepository
import com.c241ps220.centingapps.Repository.ResultRepository
import com.c241ps220.centingapps.data.database.child.Child
import com.c241ps220.centingapps.data.database.result.DetectionResult

class HistoryActivityViewModel(application: Application) : ViewModel() {
    private val mResultRepository : ResultRepository = ResultRepository(application)

    fun getHistoryByChild(id: Int):  LiveData<List<DetectionResult>> {
        return mResultRepository.getAllHistoryByIdChild(id)
    }

}