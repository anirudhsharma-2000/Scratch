package com.smitcoderx.scratch.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.smitcoderx.scratch.ui.category.CategoryScreen
import com.smitcoderx.scratch.ui.home.HomeScreen

@Composable
fun RootNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(navController, startDestination = Screens.Category.name, modifier = modifier) {
        composable(Screens.Home.name) { HomeScreen(modifier) }
        composable(Screens.Category.name) { CategoryScreen() }
    }
}
