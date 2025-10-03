package com.smitcoderx.scratch.ui.home

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.smitcoderx.scratch.Constants.TAG
import com.smitcoderx.scratch.R
import com.smitcoderx.scratch.ui.home.widgets.BottomBarState
import com.smitcoderx.scratch.ui.home.widgets.BottomToolbar
import com.smitcoderx.scratch.ui.home.widgets.CategoryFilters
import com.smitcoderx.scratch.ui.home.widgets.EmptyView
import com.smitcoderx.scratch.ui.navigation.Screens
import com.smitcoderx.scratch.ui.theme.Typography
import com.smitcoderx.scratch.ui.widgets.fadingEdge

@Composable
fun HomeScreen(modifier: Modifier = Modifier, navController: NavHostController) {
    val homeViewModel: HomeViewModel =
        viewModel(factory = HomeViewModelProvider(LocalContext.current))
    val categories = homeViewModel.categories.collectAsStateWithLifecycle().value
    val notes = homeViewModel.notes.collectAsStateWithLifecycle().value
    val selectedCategory = homeViewModel.selectedCategory.collectAsStateWithLifecycle().value
    val selectedNotes = homeViewModel.selectedNotes.collectAsStateWithLifecycle().value
    var isSheetOpen by rememberSaveable { mutableStateOf(false) }
    Log.d(TAG, "HomeScreen: $categories")
    BottomToolbar(
        selectionEnabled = notes.isNotEmpty(),
        isSelected = selectedNotes.isNotEmpty(),
        onClick = {
            when (it) {
                BottomBarState.Add -> navController.navigate("${Screens.Note.name}?noteId=")
                BottomBarState.UnSelectAll -> homeViewModel.unselectAllNotes()
                BottomBarState.SelectAll -> homeViewModel.selectAllNotes()
                BottomBarState.Delete -> homeViewModel.deleteNote()
            }
        },
        query = { homeViewModel.search(it) }) {
        Column(
            modifier.padding(horizontal = 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(stringResource(R.string.app_name), style = Typography.displayMedium)
            }
            AnimatedVisibility(selectedNotes.isEmpty()) {
                CategoryFilters(
                    categories = categories,
                    selectedCategory = selectedCategory,
                    selected = { homeViewModel.selectCategory(it) },
                    onAddFilter = { isSheetOpen = true },
                    onEditFilter = {  },
                    onDeleteFilter = {homeViewModel.deleteFilter(it)})
            }
            AnimatedVisibility(notes.isEmpty()) {
                EmptyView(modifier)
                return@AnimatedVisibility
            }
            LazyVerticalStaggeredGrid(
                modifier = Modifier
                    .fillMaxHeight()
                    .fadingEdge(
                        Brush.verticalGradient(
                            0f to Color.Transparent,
                            0.1f to Color.Black,
                            0.9f to Color.Black,
                            1f to Color.Transparent
                        )
                    ),
                columns = StaggeredGridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalItemSpacing = 4.dp
            ) {
                itemsIndexed(notes) { index, item ->
                    NoteCard(
                        note = item,
                        isSelectionEnabled = selectedNotes.contains(item.id),
                        onClick = {
                            // TODO: Add a withArgs function for clean code readability
                            if (selectedNotes.isNotEmpty()) {
                                homeViewModel.toggleNoteSelection(it)
                            } else {
                                navController.navigate("${Screens.Note.name}?noteId=${it}")
                            }
                        },
                        onLongClick = { homeViewModel.toggleNoteSelection(it) }
                    )
                }
            }
        }
        CategoryBottomSheet(modifier, isSheetOpen) { isSheetOpen = false }
    }
}
