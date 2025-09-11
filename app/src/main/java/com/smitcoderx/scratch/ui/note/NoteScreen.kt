package com.smitcoderx.scratch.ui.note

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.smitcoderx.scratch.Constants.TAG

@Composable
fun NoteScreen(modifier: Modifier = Modifier, noteId: String?) {
    val noteViewModel: NoteViewModel =
        viewModel(factory = NoteViewModelProvider(LocalContext.current))
    LaunchedEffect(Unit) {
        noteViewModel.fetchNote(noteId)
        Log.d(TAG, "NoteScreen: Fetched")
    }

}
