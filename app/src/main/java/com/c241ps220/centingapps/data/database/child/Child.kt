package com.c241ps220.centingapps.data.database.child

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "children")
data class Child(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val birthDate: String,
    val gender: String,
    val heightBirth: Float,
    val weightBirth: Float
)