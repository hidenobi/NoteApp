package com.example.noteapp.repository

import androidx.lifecycle.LiveData
import com.example.noteapp.dao.NoteDao
import com.example.noteapp.entities.Notes

class NoteRepository(private val noteDao: NoteDao) {
    val homeNotes: LiveData<MutableList<Notes>> = noteDao.getStatusNote(0)
    val doneNotes: LiveData<MutableList<Notes>> = noteDao.getStatusNote(1)
    val garbageNotes: LiveData<MutableList<Notes>> = noteDao.getStatusNote(-1)
    suspend fun addNote(notes: Notes) {
        noteDao.insertNotes(notes)
    }

    suspend fun deleteNote(notes: Notes) {
        noteDao.deleteNote(notes)
    }

    suspend fun updateNote(notes: Notes) {
        noteDao.updateNote(notes)
    }

    suspend fun deleteSpecificNote(id: Int) {
        noteDao.deleteSpecificNote(id)
    }

}