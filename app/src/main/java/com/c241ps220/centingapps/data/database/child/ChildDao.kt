package com.c241ps220.centingapps.data.database.child

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface ChildDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertChild(child: Child)

    @Update
    fun updateChild(child: Child)

    @Delete
    fun deleteChild(child: Child)

    @Query("SELECT * from children ORDER BY name ASC")
    fun getAllChild(): LiveData<List<Child>>

    @Query("SELECT * FROM children WHERE id = :childId")
    suspend fun getChildById(childId: Int): Child?
}
