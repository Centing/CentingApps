package com.c241ps220.centingapps.data.database.child

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "children")
@Parcelize
data class Child(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "birthDate")
    val birthDate: String,

    @ColumnInfo(name = "gender")
    val gender: String,

    @ColumnInfo(name = "heightBirth")
    val heightBirth: Float,
): Parcelable