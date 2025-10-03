package com.smitcoderx.scratch.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.smitcoderx.scratch.data.category.Categories
import com.smitcoderx.scratch.data.note.Note
import com.smitcoderx.scratch.ui.theme.Typography
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun NoteCard(
    modifier: Modifier = Modifier,
    note: Note,
    isSelectionEnabled: Boolean,
    onClick: (Int) -> Unit,
    onLongClick: (Int) -> Unit,
) {
    val formattedDate =
        SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH).format(note.createdAt)
    Card(
        modifier = modifier
            .clip(CardDefaults.shape)
            .combinedClickable(
                onClick = { onClick(note.id) },
                onLongClick = { onLongClick(note.id) },)
            .fillMaxWidth(),
        border = if (isSelectionEnabled) BorderStroke(2.dp, Color.Gray) else null
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            if (note.type != Categories.Encrypted.title) {
                Text(
                    modifier = Modifier.padding(top = 10.dp, start = 10.dp),
                    text = note.title,
                    style = Typography.bodyLarge
                )
                Spacer(Modifier.padding(vertical = 10.dp))
            }
            when (note.type) {
                Categories.Notes.title -> NotesView(note.content)
                Categories.Encrypted.title -> EncryptedView()
                Categories.Todo.title -> TodoView(listOf("Item 1", "Item 2"))
                Categories.List.title -> TodoView(listOf("Item 1", "Item 2")) // TODO: Change
                Categories.Scribble.title -> NotesView(note.content) // TODO: Change
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 10.dp, bottom = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AnimatedVisibility(isSelectionEnabled) {
                    Icon(
                        modifier = Modifier.size(15.dp),
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Selected"
                    )
                }
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = formattedDate,
                    textAlign = TextAlign.End,
                    style = Typography.bodySmall
                )
            }
        }
    }
}

@Composable
private fun NotesView(desc: String) {
    Text(modifier = Modifier.padding(start = 10.dp), text = desc, style = Typography.bodyMedium)
    Spacer(Modifier.padding(20.dp))
}

@Composable
private fun TodoView(todos: List<String>) {
    todos.forEach {
        Row(
            modifier = Modifier.padding(start = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(true, onCheckedChange = {})
            Text(it, style = Typography.bodyMedium)
        }
    }
}

@Composable
private fun EncryptedView() {
    Icon(
        modifier = Modifier
            .fillMaxWidth()
            .size(100.dp),
        imageVector = Icons.Default.Lock,
        contentDescription = "Lock"
    )
}
