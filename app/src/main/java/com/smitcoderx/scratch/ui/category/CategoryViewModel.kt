package com.smitcoderx.scratch.ui.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smitcoderx.scratch.data.category.Category
import com.smitcoderx.scratch.data.category.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CategoryViewModel(private val categoryRepository: CategoryRepository) : ViewModel() {
    private var _categories = MutableStateFlow<Flow<List<Category>>>(emptyFlow())
    val categories = _categories.asStateFlow()

    init {
        fetchCategories()
    }

    fun addCategory(category: Category) = viewModelScope.launch {
        categoryRepository.addCategory(category)
        _categories.value = categoryRepository.fetchCategories().stateIn(viewModelScope)
    }

    fun deleteCategory(category: Category) = viewModelScope.launch {
        categoryRepository.deleteCategory(category)
    }

    private fun fetchCategories() = viewModelScope.launch {
        _categories.value = categoryRepository.fetchCategories().stateIn(viewModelScope)
    }
}
