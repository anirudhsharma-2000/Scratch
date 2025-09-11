package com.smitcoderx.scratch.ui.note

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smitcoderx.scratch.data.category.CategoryRepository
import com.smitcoderx.scratch.data.note.Note
import com.smitcoderx.scratch.data.note.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NoteViewModel(
    private val noteRepository: NoteRepository, private val categoryRepository: CategoryRepository
) : ViewModel() {
    private var _note = MutableStateFlow<Note?>(null)
    val note = _note.asStateFlow()

    fun fetchNote(noteId: String?) = viewModelScope.launch {
        _note.value = noteRepository.fetchNote(noteId?.ifBlank { "-1" }?.toInt() ?: -1)
    }

    fun saveNote(title: String, content: String, type: String) = viewModelScope.launch {
        val note = Note(title = title, content = content, type = type)
        if (_note.value == null) {
            Log.d("saveNote: ", "New Note")
            noteRepository.addNote(note)
        } else {
            Log.d("saveNote: ", "Update Note $title")
            noteRepository.updateNote(note)
        }
    }
}
