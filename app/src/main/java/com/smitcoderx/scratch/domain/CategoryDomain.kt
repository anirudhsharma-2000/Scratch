package com.smitcoderx.scratch.domain

import com.smitcoderx.scratch.data.home.Category

object CategoryDomain {
    private val _categories = mutableListOf<Category>()

    suspend fun addCategory(category: Category) {
        _categories.add(category)
    }

    suspend fun removeCategory(category: Category) = _categories.remove(category)
    suspend fun fetchCategories(): List<Category> = _categories.toList()
}
