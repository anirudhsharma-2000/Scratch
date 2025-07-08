package com.smitcoderx.scratch.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smitcoderx.scratch.Constants.TAG
import com.smitcoderx.scratch.data.home.Category
import com.smitcoderx.scratch.domain.CategoryDomain
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CategoryViewModel : ViewModel() {
    private var _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories = _categories.asStateFlow()

    init {
        fetchCategories()
    }

    fun addCategory(category: Category) = viewModelScope.launch {
        CategoryDomain.addCategory(category)
        _categories.value = CategoryDomain.fetchCategories()
    }

    fun deleteCategory(category: Category) = viewModelScope.launch {
        CategoryDomain.removeCategory(category)
    }

    private fun fetchCategories() = viewModelScope.launch {
        val list = CategoryDomain.fetchCategories()
        Log.d(TAG, "fetchCategories: $list")
        _categories.value = list
    }
}
