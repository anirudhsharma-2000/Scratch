package com.smitcoderx.scratch.ui.home.widgets

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.RemoveCircleOutline
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalFloatingToolbar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BottomToolbar(
    selectionEnabled: Boolean,
    isSelected: Boolean,
    onClick: (BottomBarState) -> Unit,
    query: (String) -> Unit,
    content: @Composable () -> Unit
) {
    var searchVisible by rememberSaveable { mutableStateOf(false) }
    Scaffold(
        containerColor = Color.Transparent,
        bottomBar = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HorizontalFloatingToolbar(
                    modifier = Modifier.padding(40.dp),
                    expanded = true,
                    floatingActionButton = {
                        FloatingActionButton(onClick = {
                            onClick(if (selectionEnabled && isSelected) BottomBarState.Delete else BottomBarState.Add)
                        }) {
                            if (selectionEnabled && isSelected) Icon(
                                Icons.Default.Delete, contentDescription = "Delete"
                            )
                            else Icon(Icons.Default.Add, contentDescription = "Add")
                        }
                    }) {
                    if (selectionEnabled && isSelected) {
                        IconButton(onClick = {
                            if (selectionEnabled && isSelected) onClick(BottomBarState.SelectAll)
                        }) {
                            Icon(
                                Icons.Default.CheckCircleOutline, contentDescription = "Select All"
                            )
                        }
                        IconButton(onClick = {
                            if (selectionEnabled && isSelected) onClick(BottomBarState.UnSelectAll)
                        }) {
                            Icon(
                                Icons.Default.RemoveCircleOutline,
                                contentDescription = "Un Select All"
                            )
                        }
                    } else {
                        IconButton(onClick = { searchVisible = searchVisible.not() }) {
                            Icon(
                                if (searchVisible) Icons.Default.SearchOff else Icons.Default.Search,
                                contentDescription = "Search"
                            )
                        }
                    }
                }
                AnimatedVisibility(visible = searchVisible) {
                    Box(
                        modifier = Modifier
                            .background(
                                MaterialTheme.colorScheme.surface,
                                RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                            )
                            .padding(10.dp)
                    ) { SearchBar { query(it) } }
                }
            }
        }
    ) { content() }
}

enum class BottomBarState() {
    Add,
    Delete,
    SelectAll,
    UnSelectAll
}
