package com.c241ps220.centingapps.views.AnakSection.DetailAnak

import android.app.Application
import androidx.lifecycle.ViewModel
import com.c241ps220.centingapps.Repository.ChildRepository
import com.c241ps220.centingapps.data.database.child.Child

class DetailAnakViewModel (application: Application) : ViewModel() {

    private val mChildRepository: ChildRepository = ChildRepository(application)

    fun update(child: Child) {
        mChildRepository.update(child)
    }
    fun delete(child: Child) {
        mChildRepository.delete(child)
    }

}