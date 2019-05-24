package com.lena.notes.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.lena.notes.data.local.ToDoDatabase
import com.lena.notes.data.model.Note

class NotesRepository(application: Application) : NotesDataSource {

    private var localDataSource : NotesDataSource = ToDoDatabase.getInstance(application).noteDao()

    override fun getNotesOrderBy() : LiveData<List<Note>> {
        return localDataSource.getNotesOrderBy()
    }

    override fun getNotesOrderByDESC() : LiveData<List<Note>> {
        return localDataSource.getNotesOrderByDESC()
    }

    override fun getNote(noteId: Int) : LiveData<Note> {
        return localDataSource.getNote(noteId)
    }

    override fun insertNote(note: Note) {
        localDataSource.insertNote(note)
    }

    override fun updateNote(note: Note) {
        localDataSource.updateNote(note)
    }

    override fun deleteNote(note: Note) {
        localDataSource.deleteNote(note)
    }
}