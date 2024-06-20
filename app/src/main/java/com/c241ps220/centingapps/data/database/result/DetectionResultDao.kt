package com.c241ps220.centingapps.data.database.result

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DetectionResultDao {
    @Insert
    fun insertDetectionResult(result: DetectionResult)

    @Query("SELECT * FROM detection_results WHERE childId = :childId")
    fun getResultsByChildId(childId: Int): LiveData<List<DetectionResult>>

    @Query("SELECT * FROM detection_results")
    fun getAllResults(): List<DetectionResult>
}
