package com.c241ps220.centingapps.Repository

import androidx.recyclerview.widget.DiffUtil
import com.c241ps220.centingapps.data.database.result.DetectionResult

class ResultDiffCallback (private val oldNoteList: List<DetectionResult>, private val newNoteList: List<DetectionResult>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldNoteList.size
    override fun getNewListSize(): Int = newNoteList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldNoteList[oldItemPosition].childName == newNoteList[newItemPosition].childName
    }
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldNote = oldNoteList[oldItemPosition]
        val newNote = newNoteList[newItemPosition]
        return oldNote.childName == newNote.childName && oldNote.childAge == newNote.childAge && oldNote.childLastHeight == newNote.childLastHeight && oldNote.childDetectionResult == newNote.childDetectionResult && oldNote.childDetectionDate == newNote.childDetectionDate
    }
}