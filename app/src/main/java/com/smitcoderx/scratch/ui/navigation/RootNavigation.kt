package com.smitcoderx.scratch.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.smitcoderx.scratch.ui.category.CategoryScreen
import com.smitcoderx.scratch.ui.home.HomeScreen
import com.smitcoderx.scratch.ui.note.NoteScreen

@Composable
fun RootNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(navController, startDestination = Screens.Home.name, modifier = modifier) {
        composable(Screens.Home.name) { HomeScreen(modifier, navController) }
        composable(Screens.Category.name) { CategoryScreen() }
        // TODO: add new param isNew or something
        composable(
            route = "${Screens.Note.name}?noteId={id}",
            arguments = listOf(navArgument("id") {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            })
        ) { NoteScreen(modifier, it.arguments?.getString("id")) }
    }
}
