package com.smitcoderx.scratch.data.category

class CategoryRepository(private val categoryDao: CategoryDao) {
    suspend fun addCategory(category: Category) = categoryDao.addCategory(category)

    suspend fun updateCategory(category: Category) = categoryDao.updateCategory(category)

    suspend fun deleteCategory(category: Category) = categoryDao.deleteCategory(category)

    fun fetchCategories() = categoryDao.fetchCategories()
}
