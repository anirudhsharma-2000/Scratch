package com.smitcoderx.scratch

import androidx.compose.runtime.Composable

sealed class DataState<out T> {
    object Loading : DataState<Nothing>()
    data class Error(val message: String) : DataState<Nothing>()
    data class Success<T>(val data: T) : DataState<T>()

    // TODO: Introduce data for viewModels so don't have to check for State.
}
