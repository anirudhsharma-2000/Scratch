package com.smitcoderx.scratch.ui.category

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun CategoryScreen(modifier: Modifier = Modifier) {
    val viewModel: CategoryViewModel =
        viewModel(factory = CategoryViewModelProvider(LocalContext.current))
}
