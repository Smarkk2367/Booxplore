package com.example.booxplore.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.booxplore.ui.navigation.detailRoute
//for retrofit testing
import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import com.example.booxplore.data.repository.BookRepository

@Composable
fun HomeScreen(
    navController: NavController
) {
    LaunchedEffect(Unit) {
        val repo = BookRepository()
        val result = repo.getFictionBooks()
        result.onSuccess { books ->
            Log.d("BOOKS", "Loaded ${books.size} books")
            books.take(5).forEach { Log.d("BOOKS", it.title) }
        }.onFailure { e ->
            Log.e("BOOKS", "Failed to load books", e)
        }
    }
    Column {
        Text("Home Screen")

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                navController.navigate(detailRoute("TEST_ID"))
            }
        ) {
            Text("Go to Detail")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { navController.navigate("favorites") }) {
            Text("Go to Favorites")
        }
    }
}