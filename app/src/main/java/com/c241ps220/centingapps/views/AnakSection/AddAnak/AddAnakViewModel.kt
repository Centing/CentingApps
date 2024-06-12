package com.c241ps220.centingapps.views.AnakSection.AddAnak

import android.app.Application
import androidx.lifecycle.ViewModel
import com.c241ps220.centingapps.Repository.ChildRepository
import com.c241ps220.centingapps.data.database.child.Child

class AddAnakViewModel (application: Application) : ViewModel() {

    private val mChildRepository: ChildRepository = ChildRepository(application)

    fun insert(child: Child) {
        mChildRepository.insert(child)
    }

}