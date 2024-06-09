package com.c241ps220.centingapps.data.database.result

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DetectionResultDao {
    @Insert
    suspend fun insertDetectionResult(result: DetectionResult): Long

    @Query("SELECT * FROM detection_results WHERE childId = :childId")
    suspend fun getResultsByChildId(childId: Int): List<DetectionResult>

    @Query("SELECT * FROM detection_results")
    suspend fun getAllResults(): List<DetectionResult>
}
