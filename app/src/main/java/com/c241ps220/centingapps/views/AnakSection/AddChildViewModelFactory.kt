package com.c241ps220.centingapps.views.AnakSection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.c241ps220.centingapps.data.database.child.ChildDao

class AddChildViewModelFactory(private val childDao: ChildDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddChildViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddChildViewModel(childDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}