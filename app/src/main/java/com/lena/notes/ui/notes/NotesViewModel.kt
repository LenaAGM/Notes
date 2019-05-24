package com.lena.notes.ui.notes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.lena.notes.data.NotesRepository
import com.lena.notes.data.model.Note

class NotesViewModel(application: Application) : AndroidViewModel(application) {

    private var repository : NotesRepository = NotesRepository(application)

    var notesLiveData : LiveData<List<Note>>
    var fullNotes = mutableListOf<Note>()
    var notes = mutableListOf<Note>()

    init {
        notesLiveData = repository.getNotesOrderByDESC()
    }

    fun getNotesOrderBy() {
        notesLiveData = repository.getNotesOrderBy()
    }

    fun getNotesOrderByDESC() {
        notesLiveData = repository.getNotesOrderByDESC()
    }

    fun updateData(list : List<Note>) {

        notes.clear()
        notes.addAll(list)

        fullNotes.clear()
        fullNotes.addAll(list)
    }

    fun updateData(searchText : String) {
        notes.clear()
        fullNotes.forEach {
            if (it.text.toLowerCase().contains(searchText.toLowerCase().trim())) {
                notes.add(it)
            }
        }
    }
}