package com.c241ps220.centingapps.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.c241ps220.centingapps.data.database.child.Child
import com.c241ps220.centingapps.data.database.child.ChildDao
import com.c241ps220.centingapps.data.database.result.DetectionResult
import com.c241ps220.centingapps.data.database.result.DetectionResultDao

@Database(entities = [Child::class, DetectionResult::class], version = 2) // Incremented version number
abstract class AppDatabase : RoomDatabase() {
    abstract fun childDao(): ChildDao
    abstract fun detectionResultDao(): DetectionResultDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        AppDatabase::class.java, "centing_apps_database")
                        .build()
                }
            }
            return INSTANCE as AppDatabase
        }
    }
}
