package com.c241ps220.centingapps.Repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.c241ps220.centingapps.data.database.AppDatabase
import com.c241ps220.centingapps.data.database.result.DetectionResult
import com.c241ps220.centingapps.data.database.result.DetectionResultDao
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ResultRepository(application: Application) {
    private val mDetectionResultDao: DetectionResultDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    init {
        val db = AppDatabase.getInstance(application)
        mDetectionResultDao = db.detectionResultDao()
    }

    fun getAllHistoryByIdChild(id: Int): LiveData<List<DetectionResult>> = mDetectionResultDao.getResultsByChildId(id)

    fun insert(detectionResult: DetectionResult) {
        executorService.execute { mDetectionResultDao.insertDetectionResult(detectionResult) }
    }

}