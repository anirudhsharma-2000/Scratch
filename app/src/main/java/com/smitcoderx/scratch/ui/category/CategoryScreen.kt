package com.smitcoderx.scratch.ui.category

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.smitcoderx.scratch.data.category.Categories

@Composable
fun CategoryScreen(modifier: Modifier = Modifier) {
    val viewModel: CategoryViewModel =
        viewModel(factory = CategoryViewModelProvider(LocalContext.current))
    val createdCategories = viewModel.categories.collectAsStateWithLifecycle().value
    val categories = Categories.entries
}
