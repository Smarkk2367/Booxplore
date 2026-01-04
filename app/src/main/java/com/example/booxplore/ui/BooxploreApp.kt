package com.example.booxplore.ui


import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.booxplore.ui.home.HomeScreen
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.booxplore.ui.navigation.Routes
import com.example.booxplore.ui.detail.BookDetailScreen
import com.example.booxplore.ui.favorites.FavoritesScreen

@Composable
fun BooxploreApp() {
    val navController = rememberNavController()
    Scaffold { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.HOME,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Routes.HOME) {
                HomeScreen(navController)
            }

            composable(
                route = Routes.DETAIL,
                arguments = listOf(navArgument("bookId") {
                    type = NavType.StringType
                })
            ) {
                BookDetailScreen()
            }

            composable(Routes.FAVORITES) {
                FavoritesScreen()
            }
        }
    }
}