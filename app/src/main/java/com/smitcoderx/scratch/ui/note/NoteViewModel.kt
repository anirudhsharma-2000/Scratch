package com.smitcoderx.scratch.ui.note

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smitcoderx.scratch.DataState
import com.smitcoderx.scratch.data.category.CategoryRepository
import com.smitcoderx.scratch.data.note.Note
import com.smitcoderx.scratch.data.note.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NoteViewModel(
    private val noteRepository: NoteRepository, private val categoryRepository: CategoryRepository
) : ViewModel() {
    private var _note = MutableStateFlow<DataState<Note>>(DataState.Loading)
    val note = _note.asStateFlow()

    fun fetchNote(noteId: String?) = viewModelScope.launch {
        val note = noteRepository.fetchNote(noteId?.toInt())
        if (note != null) {
            _note.value = DataState.Success(note)
        } else {
            _note.value = DataState.Error("Something Went Wrong")
        }
    }

    fun saveNote(title: String, content: String, type: String) = viewModelScope.launch {
        val note = Note(title = title, content = content, type = type)
        noteRepository.addNote(note)
    }

    fun updateNote(id: Int, title: String, content: String, type: String) = viewModelScope.launch {
        val note = Note(id, title = title, content = content, type = type)
        Log.d("saveNote: ", "Update Note $title")
        noteRepository.updateNote(note)
    }
}
