package com.smitcoderx.scratch.ui.home

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalFloatingToolbar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.smitcoderx.scratch.R
import com.smitcoderx.scratch.data.category.Category
import com.smitcoderx.scratch.ui.navigation.Screens
import com.smitcoderx.scratch.ui.theme.Typography

@Composable
fun HomeScreen(modifier: Modifier = Modifier, navController: NavHostController) {
    val homeViewModel: HomeViewModel =
        viewModel(factory = HomeViewModelProvider(LocalContext.current))
    val categories = homeViewModel.categories.collectAsStateWithLifecycle().value
    val notes = homeViewModel.notes.collectAsStateWithLifecycle().value
    val selectedNotes = remember { mutableListOf<Int>() }
    var isSelectionEnabled by remember { mutableStateOf(false) }
    BottomToolbar(
        onClick = { navController.navigate("${Screens.Note.name}?noteId=") },
        query = { it }) {
        Column(
            modifier.padding(horizontal = 10.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(stringResource(R.string.app_name), style = Typography.displayMedium)
            }
            CategoryFilters(categories) { homeViewModel.fetchByCategory(it.id) }
            LazyVerticalStaggeredGrid(
                modifier = Modifier.fillMaxHeight(),
                columns = StaggeredGridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalItemSpacing = 4.dp
            ) {
                itemsIndexed(notes) { index, item ->
                    NoteCard(
                        note = item,
                        isSelectionEnabled = isSelectionEnabled,
                        onClick = {
                            // TODO: Add a withArgs function from clean code
                            navController.navigate("${Screens.Note.name}?noteId=${item.id}")
                        },
                        onLongClick = { isSelectionEnabled = it },
                        selectedId = { selectedNotes.add(it) })
                }
            }
        }
    }
}

@Composable
private fun CategoryFilters(categories: List<Category>, selected: (Category) -> Unit) {
    var selected by remember { mutableStateOf<Category?>(null) }
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        categories.forEach {
            FilterChip(
                selected = selected == it,
                onClick = {
                    selected = it
                    selected(it)
                },
                label = {
                    Text(
                        modifier = Modifier.padding(vertical = 12.dp, horizontal = 8.dp),
                        text = it.name,
                        style = Typography.labelMedium
                    )
                }, shape = RoundedCornerShape(30.dp)
            )
        }
    }
}

@Composable
private fun SearchBar(query: (String) -> Unit) {
    var search by remember { mutableStateOf("") }
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = search,
        minLines = 1,
        shape = RoundedCornerShape(20.dp),
        onValueChange = { search = it },
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { query(search) }),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        placeholder = {
            Text(
                text = "Search here 🔎",
                style = Typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface.copy(0.6f),
            )
        }
    )
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun BottomToolbar(
    onClick: () -> Unit,
    query: (String) -> Unit,
    content: @Composable () -> Unit,
) {
    var searchVisible by remember { mutableStateOf(false) }
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
                        FloatingActionButton(onClick = { onClick() }) {
                            Icon(Icons.Default.Add, contentDescription = "Add")
                        }
                    }) {
                    IconButton(onClick = { searchVisible = searchVisible.not() }) {
                        Icon(
                            if (searchVisible) Icons.Default.SearchOff else Icons.Default.Search,
                            contentDescription = "Search"
                        )
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
