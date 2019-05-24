package com.lena.notes.data

import androidx.lifecycle.LiveData
import com.lena.notes.data.model.Note

interface NotesDataSource {
    fun getNotesOrderBy() : LiveData<List<Note>>
    fun getNotesOrderByDESC() : LiveData<List<Note>>
    fun getNote(noteId: Int) : LiveData<Note>
    fun insertNote(note: Note)
    fun updateNote(note: Note)
    fun deleteNote(note: Note)
}