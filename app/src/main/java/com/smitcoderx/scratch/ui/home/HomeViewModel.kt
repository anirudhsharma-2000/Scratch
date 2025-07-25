package com.smitcoderx.scratch.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smitcoderx.scratch.data.category.Category
import com.smitcoderx.scratch.data.category.CategoryRepository
import com.smitcoderx.scratch.data.note.Note
import com.smitcoderx.scratch.data.note.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val noteRepository: NoteRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {
    private var _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories = _categories.asStateFlow()
    private var _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes = _notes.asStateFlow()

    init {
        fetchCategories()
        fetchNotes()
    }

    fun addNote(note: Note) = viewModelScope.launch {
        noteRepository.addNote(note)
        _notes.value = noteRepository.fetchNotes()
    }

    private fun fetchNotes() = viewModelScope.launch {
        _notes.value = noteRepository.fetchNotes()
    }

    fun fetchByCategory(categoryId: Int) = fetchNotesByCategories(categoryId)

    private fun fetchNotesByCategories(categoryId: Int) = viewModelScope.launch {
        _notes.value = noteRepository.fetchNotesByCategory(categoryId)
    }

    private fun fetchCategories() = viewModelScope.launch {
        _categories.value = categoryRepository.fetchCategories()
    }
}
