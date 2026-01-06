package com.example.booxplore.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.booxplore.data.local.FavoritesManager
import com.example.booxplore.data.local.ThemeManager
import com.example.booxplore.data.remote.RetrofitProvider
import com.example.booxplore.data.repository.BookRepository
import com.example.booxplore.ui.detail.BookDetailScreen
import com.example.booxplore.ui.detail.BookDetailViewModel
import com.example.booxplore.ui.favorites.FavoritesScreen
import com.example.booxplore.ui.favorites.FavoritesViewModel
import com.example.booxplore.ui.home.HomeScreen
import com.example.booxplore.ui.home.HomeViewModel

@Composable
fun BooxploreApp() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val favoritesManager = remember { FavoritesManager(context) }
    val themeManager = remember { ThemeManager(context) }
    val repository = remember { BookRepository(RetrofitProvider.api) }
    val homeViewModel = remember { HomeViewModel(repository) }
    val favoritesViewModel = remember { FavoritesViewModel(favoritesManager, repository) }

    val isDarkTheme by themeManager.isDarkTheme.collectAsState()
    val currentTheme by themeManager.currentTheme.collectAsState()

    com.example.booxplore.ui.theme.BooxploreTheme(
        darkTheme = isDarkTheme,
        currentTheme = currentTheme
    ) {
        
        Scaffold(
            topBar = {
                @OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
                androidx.compose.material3.TopAppBar(
                    title = { Text("Booxplore") },
                    actions = {
                        IconButton(onClick = { 
                            navController.navigate("settings") {
                                launchSingleTop = true
                            }
                        }) {
                            Icon(
                                imageVector = androidx.compose.material.icons.Icons.Default.Settings,
                                contentDescription = "Settings"
                            )
                        }
                    }
                )
            },
            bottomBar = {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                val bottomBarRoutes = listOf("home", "favorites")
                
                if (currentRoute in bottomBarRoutes) {
                    NavigationBar {
                        NavigationBarItem(
                            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                            label = { Text("Home") },
                            selected = currentRoute == "home",
                            onClick = {
                                navController.navigate("home") {
                                    popUpTo("home") { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                        NavigationBarItem(
                            icon = { Icon(Icons.Default.Favorite, contentDescription = "Favorites") },
                            label = { Text("Favorites") },
                            selected = currentRoute == "favorites",
                            onClick = {
                                navController.navigate("favorites") {
                                    popUpTo("home") { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "home",
                modifier = Modifier.padding(innerPadding)
            ) {
                composable("home") {
                    HomeScreen(
                        viewModel = homeViewModel,
                        onBookClick = { bookId ->
                            navController.navigate("details/$bookId")
                        }
                    )
                }

                composable("favorites") {
                    FavoritesScreen(
                        viewModel = favoritesViewModel,
                        onBookClick = { bookId ->
                            navController.navigate("details/$bookId")
                        }
                    )
                }
                
                composable("settings") {
                    com.example.booxplore.ui.settings.SettingsScreen(themeManager = themeManager)
                }

                composable(
                    route = "details/{bookId}",
                    arguments = listOf(navArgument("bookId") { type = NavType.StringType })
                ) { backStackEntry ->
                    val bookId = backStackEntry.arguments?.getString("bookId") ?: ""
                    val detailViewModel = remember(bookId) { 
                        BookDetailViewModel(repository, favoritesManager, bookId) 
                    }
                    
                    BookDetailScreen(viewModel = detailViewModel)
                }
            }
        }
    }
}