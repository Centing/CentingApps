package com.c241ps220.centingapps.views.Deteksi.Result.ByUser

import android.app.Application
import androidx.lifecycle.ViewModel
import com.c241ps220.centingapps.Repository.ResultRepository
import com.c241ps220.centingapps.data.database.result.DetectionResult

class ResultDetectByUserViewModel (application: Application) : ViewModel() {

    private val mResultRepository : ResultRepository = ResultRepository(application)

    fun insert(detectionResult: DetectionResult) {
        mResultRepository.insert(detectionResult)
    }

}