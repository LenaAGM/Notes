package com.lena.notes.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.lena.notes.data.NotesDataSource
import com.lena.notes.data.model.Note

@Dao
interface NotesDao : NotesDataSource {

    @Query("SELECT * FROM notes ORDER BY date")
    override fun getNotesOrderBy() : LiveData<List<Note>>

    @Query("SELECT * FROM notes ORDER BY date DESC")
    override fun getNotesOrderByDESC() : LiveData<List<Note>>

    @Query("SELECT * FROM notes WHERE id = :noteId")
    override fun getNote(noteId: Int) : LiveData<Note>

    @Insert
    override fun insertNote(note: Note)

    @Update
    override fun updateNote(note: Note)

    @Delete
    override fun deleteNote(note: Note)
}