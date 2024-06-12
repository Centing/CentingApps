package com.c241ps220.centingapps.Repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.c241ps220.centingapps.data.database.AppDatabase
import com.c241ps220.centingapps.data.database.child.Child
import com.c241ps220.centingapps.data.database.child.ChildDao
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ChildRepository(application: Application) {
    private val mChildDao: ChildDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    init {
        val db = AppDatabase.getInstance(application)
        mChildDao = db.childDao()
    }

    fun getAllChild(): LiveData<List<Child>> = mChildDao.getAllChild()

    fun insert(child: Child) {
        executorService.execute { mChildDao.insertChild(child) }
    }

    fun update(child: Child) {
        executorService.execute { mChildDao.updateChild(child) }
    }

    fun delete(child: Child) {
        executorService.execute { mChildDao.deleteChild(child) }
    }
}