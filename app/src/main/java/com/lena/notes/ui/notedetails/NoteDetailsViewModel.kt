package com.lena.notes.ui.notedetails

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.AndroidViewModel
import com.lena.notes.data.NotesRepository
import com.lena.notes.data.model.Note

class NoteDetailsViewModel(application: Application) : AndroidViewModel(application) {

    private var repository : NotesRepository = NotesRepository(application)

    var note : Note = Note(0, "", System.currentTimeMillis())

    var textOld = ""

    private fun saveNote(note : Note) {
        DoAsync {
            if (note.id == 0) {
                repository.insertNote(note)
            } else if (note.text != textOld) {
                note.date = System.currentTimeMillis()
                repository.updateNote(note)
            }
        }.execute()
    }

    fun delete() {
        if (note.id != 0) {
            DoAsync {
                repository.deleteNote(note)
            }.execute()
        } else {
            note.text = ""
        }
    }

    class DoAsync(val handler: () -> Unit) : AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg params: Void?): Void? {
            handler()
            return null
        }
    }

    override fun onCleared() {
        super.onCleared()
        if (note.text.trim().isNotEmpty()) {
            saveNote(note)
        }
    }
}