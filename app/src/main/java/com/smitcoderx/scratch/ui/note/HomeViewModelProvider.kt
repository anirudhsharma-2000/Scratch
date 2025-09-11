package com.smitcoderx.scratch.ui.note

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.smitcoderx.scratch.data.category.CategoryRepository
import com.smitcoderx.scratch.data.note.NoteRepository
import com.smitcoderx.scratch.database.AppDatabase

class NoteViewModelProvider(val context: Context) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NoteViewModel(
            noteRepository = NoteRepository(AppDatabase.getDatabase(context).noteDao()),
            categoryRepository = CategoryRepository(
                AppDatabase.getDatabase(context).categoryDao()
            )
        ) as T
    }
}
