package com.smitcoderx.scratch.ui.note

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.smitcoderx.scratch.Constants.TAG
import com.smitcoderx.scratch.DataState
import com.smitcoderx.scratch.data.category.Categories
import com.smitcoderx.scratch.data.note.Note
import com.smitcoderx.scratch.ui.theme.Typography

@Composable
fun NoteScreen(modifier: Modifier = Modifier, noteId: String?) {
    val noteViewModel: NoteViewModel =
        viewModel(factory = NoteViewModelProvider(LocalContext.current))
    LaunchedEffect(Unit) {
        if (noteId.isNullOrBlank()) return@LaunchedEffect
        noteViewModel.fetchNote(noteId)
        Log.d(TAG, "NoteScreen: Fetched")
    }
    val note = noteViewModel.note.collectAsStateWithLifecycle().value
    when (note) {
        is DataState.Loading -> {
            RenderNoteScreen(modifier, null) { title, content ->
                noteViewModel.saveNote(title, content, Categories.Notes.title)
            }
        }

        is DataState.Error -> {

        }

        is DataState.Success -> {
            RenderNoteScreen(modifier, note.data) { title, content ->
                noteViewModel.updateNote(note.data.id, title, content, note.data.type)
            }
        }
    }
}

@Composable
fun RenderNoteScreen(
    modifier: Modifier = Modifier,
    note: Note?,
    onSaveNote: (String, String) -> Unit
) {
    var title by remember { mutableStateOf(note?.title) }
    var content by remember { mutableStateOf(note?.content) }
    Column(modifier) {
        RenderTextField(
            note?.title,
            "Title",
            Typography.titleLarge.copy(color = LocalContentColor.current)
        ) {
            title = it
        }
        Spacer(Modifier.padding(vertical = 10.dp))
        RenderTextField(
            note?.content,
            "Write Something here....",
            Typography.bodyLarge.copy(color = LocalContentColor.current)
        ) {
            content = it
        }
    }
    DisposableEffect(Unit) {
        onDispose {
            title?.let { title ->
                content?.let { content ->
                    onSaveNote(title, content)
                }
            }
            Log.d(TAG, "RenderNoteScreen: Saved")
        }
    }
}

@Composable
private fun RenderTextField(
    text: String?,
    hint: String,
    style: TextStyle,
    onTextChange: (String) -> Unit
) {
    var newText by remember { mutableStateOf(text ?: "") }
    Log.d(TAG, "RenderTextField: $newText")
    BasicTextField(
        newText,
        onValueChange = {
            newText = it
            onTextChange(newText)
        },
        decorationBox = {
            Box {
                AnimatedVisibility(newText.isBlank()) {
                    Text(text = hint, style = style.copy(color = Color.Gray))
                }
                it()
            }
        },
        textStyle = style,
        cursorBrush = SolidColor(LocalContentColor.current.copy(alpha = 0.3f)),
        keyboardOptions = KeyboardOptions.Default,
        keyboardActions = KeyboardActions(onDone = { }),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp)
    )
}
