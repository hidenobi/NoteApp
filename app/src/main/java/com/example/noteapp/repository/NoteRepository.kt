package com.example.noteapp.repository

import androidx.lifecycle.LiveData
import com.example.noteapp.dao.NoteDao
import com.example.noteapp.entities.Notes

class NoteRepository(private val noteDao: NoteDao) {
    val readAllData: LiveData<MutableList<Notes>> = noteDao.getAllNotes()
    suspend fun addNote(notes: Notes){
        noteDao.insertNotes(notes)
    }
    suspend fun deleteNote(notes: Notes){
        noteDao.deleteNote(notes)
    }
    suspend fun updateNote(notes: Notes){
        noteDao.updateNote(notes)
    }
    suspend fun deleteSpecificNote(id:Int){
        noteDao.deleteSpecificNote(id)
    }

}