package com.c241ps220.centingapps.data.database.result

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "detection_results")
data class DetectionResult(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val childId: String,
    val childName: String,
    val childAge: String,
    val childLastHeight: String,
    val childDetectionResult: String,
    val childDetectionDate: String,
)