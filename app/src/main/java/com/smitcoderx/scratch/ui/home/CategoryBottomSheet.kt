package com.smitcoderx.scratch.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryBottomSheet(modifier: Modifier = Modifier, isSheetOpen: Boolean, onDismiss: () -> Unit) {
    val sheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()
    AnimatedVisibility(isSheetOpen) {
        ModalBottomSheet(modifier = modifier, sheetState = sheetState, onDismissRequest = {
            onDismiss()
            coroutineScope.launch {
                sheetState.hide()
            }
        }) {
            Column {
                Text("Testing")
                Button(onClick = {
                    coroutineScope.launch {
                        sheetState.hide()
                    }
                    onDismiss()
                }) { Text("Close") }
            }
        }
    }
}
