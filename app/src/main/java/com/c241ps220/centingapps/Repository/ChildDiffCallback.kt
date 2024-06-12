package com.c241ps220.centingapps.Repository

import androidx.recyclerview.widget.DiffUtil
import com.c241ps220.centingapps.data.database.child.Child

class ChildDiffCallback (private val oldNoteList: List<Child>, private val newNoteList: List<Child>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldNoteList.size
    override fun getNewListSize(): Int = newNoteList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldNoteList[oldItemPosition].name == newNoteList[newItemPosition].name
    }
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldNote = oldNoteList[oldItemPosition]
        val newNote = newNoteList[newItemPosition]
        return oldNote.name == newNote.name && oldNote.birthDate == newNote.birthDate && oldNote.gender == newNote.gender && oldNote.heightBirth == newNote.heightBirth
    }
}