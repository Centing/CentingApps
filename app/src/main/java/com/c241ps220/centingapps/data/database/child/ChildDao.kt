package com.c241ps220.centingapps.data.database.child

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ChildDao {
    @Insert
    suspend fun insertChild(child: Child): Long

    @Update
    suspend fun updateChild(child: Child)

    @Delete
    suspend fun deleteChild(child: Child)

    @Query("SELECT * FROM children")
    suspend fun getAllChildren(): List<Child>
}
