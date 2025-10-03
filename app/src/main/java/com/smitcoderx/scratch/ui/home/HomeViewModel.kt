package com.smitcoderx.scratch.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smitcoderx.scratch.Constants.TAG
import com.smitcoderx.scratch.data.category.Category
import com.smitcoderx.scratch.data.category.CategoryRepository
import com.smitcoderx.scratch.data.note.NoteRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val noteRepository: NoteRepository, categoryRepository: CategoryRepository
) : ViewModel() {
    private val _selectedCategory = MutableStateFlow<Category?>(null)
    val selectedCategory = _selectedCategory.asStateFlow()
    private val _selectedNotes = MutableStateFlow<Set<Int>>(setOf())
    val selectedNotes = _selectedNotes.asStateFlow()

    val categories = categoryRepository.fetchCategories()
        .stateIn(viewModelScope, started = SharingStarted.WhileSubscribed(5000), emptyList())

    @OptIn(ExperimentalCoroutinesApi::class)
    val notes = selectedCategory.flatMapLatest {
        if (it == null) {
            noteRepository.fetchNotes()
        } else {
            noteRepository.fetchNotesByCategory(it.id)
        }
    }.stateIn(viewModelScope, started = SharingStarted.WhileSubscribed(5000), emptyList())

    fun toggleNoteSelection(id: Int) {
        if (_selectedNotes.value.contains(id)) unSelectNote(id) else selectNote(id)
        Log.d(TAG, "setNoteSelected: ${_selectedNotes.value}")
    }

    private fun unSelectNote(id: Int) {
        _selectedNotes.value = _selectedNotes.value - id
    }

    private fun selectNote(id: Int) {
        _selectedNotes.value = _selectedNotes.value + id
    }

    fun selectAllNotes() {
        notes.value.forEach { selectNote(it.id) }
    }

    fun unselectAllNotes() {
        notes.value.forEach {
            unSelectNote(it.id)
        }
    }

    fun selectCategory(category: Category?) {
//        _selectedCategory.value = category
    }

    fun deleteNote() = viewModelScope.launch {
        noteRepository.deleteNotes(selectedNotes.value)
        _selectedNotes.value = emptySet()
    }

    // TODO: the searching logic is fuzzy, need to improve basis on its title and content available.
    fun search(query: String) {
        notes.value.filter { it.title.contains(query) }
    }
}
