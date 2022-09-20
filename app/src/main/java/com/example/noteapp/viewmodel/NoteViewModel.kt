package com.example.noteapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.noteapp.database.NotesDatabase
import com.example.noteapp.entities.Notes
import com.example.noteapp.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {
    val homeNotes: LiveData<MutableList<Notes>>
    val doneNotes: LiveData<MutableList<Notes>>
    val garbageNotes: LiveData<MutableList<Notes>>
    private val repository: NoteRepository

    init {
        val noteDao = NotesDatabase.getDatabase(application).noteDao()
        repository = NoteRepository(noteDao)
        homeNotes = repository.homeNotes
        doneNotes = repository.doneNotes
        garbageNotes = repository.garbageNotes
    }

    fun addNote(notes: Notes) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addNote(notes)
        }
    }

    fun updateNote(notes: Notes) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateNote(notes)
        }
    }

    fun deleteSpecificNote(id: Int) {
        viewModelScope.launch {
            repository.deleteSpecificNote(id)
        }
    }

    fun searchByTitle(searchQuery: String, statusNote: Int): LiveData<MutableList<Notes>> =
        repository.searchByTitle(searchQuery, statusNote)


}