package com.example.noteapp.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.noteapp.entities.Notes

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes ORDER BY id DESC")
    fun getAllNotes(): LiveData<MutableList<Notes>>

    @Query("SELECT * FROM notes WHERE id =:id")
    fun getSpecificNote(id: Int): Notes

    @Query("SELECT * FROM notes WHERE statusNote=:statusNote")
    fun getStatusNote(statusNote: Int): LiveData<MutableList<Notes>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotes(note: Notes)

    @Delete
    suspend fun deleteNote(note: Notes)

    @Query("DELETE FROM notes WHERE id =:id")
    suspend fun deleteSpecificNote(id: Int)

    @Update
    suspend fun updateNote(note: Notes)
}