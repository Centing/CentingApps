package com.c241ps220.centingapps.views.AnakSection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.c241ps220.centingapps.data.database.child.Child
import com.c241ps220.centingapps.data.database.child.ChildDao
import kotlinx.coroutines.launch

class AddChildViewModel(private val childDao: ChildDao) : ViewModel() {

    fun addChild(name: String, birthDate: String, gender: String, heightBirth: Float) {
        val child = Child(
            name = name,
            birthDate = birthDate,
            gender = gender,
            heightBirth = heightBirth,
            )
        viewModelScope.launch {
            childDao.insertChild(child)
        }
    }
}