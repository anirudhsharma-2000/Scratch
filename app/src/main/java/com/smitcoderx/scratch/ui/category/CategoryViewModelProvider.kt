package com.smitcoderx.scratch.ui.category

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.smitcoderx.scratch.data.category.CategoryRepository
import com.smitcoderx.scratch.database.AppDatabase

class CategoryViewModelProvider(val context: Context) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CategoryViewModel(
            CategoryRepository(AppDatabase.getDatabase(context).categoryDao())) as T
    }
}
