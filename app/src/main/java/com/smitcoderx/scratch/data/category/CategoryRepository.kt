package com.smitcoderx.scratch.data.category

import kotlinx.coroutines.flow.map

class CategoryRepository(private val categoryDao: CategoryDao) {
    suspend fun addCategory(category: Category) = categoryDao.addCategory(category)

    suspend fun updateCategory(category: Category) = categoryDao.updateCategory(category)

    suspend fun deleteCategory(category: Category) = categoryDao.deleteCategory(category)

    fun fetchCategories() = categoryDao.fetchCategories().map {
        // TODO: To finalize if we need the Add or not
        it.apply { it.add(0, Category(id = 0, name = "All", type = "All", color = 0L)) }
    }
}
