package com.c241ps220.centingapps.data.database.result

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "detection_results")
data class DetectionResult(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val childId: Int,
    val age: Float,
    val height: Float,
    val weight: Float,
    val result: String
)