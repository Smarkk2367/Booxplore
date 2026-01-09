package com.example.booxplore.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.booxplore.ui.components.BookList
import com.example.booxplore.ui.error.ErrorContent

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onBookClick: (String) -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { 
                searchQuery = it
                viewModel.searchBooks(it) 
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            placeholder = { Text("Search books...") },
            singleLine = true
        )

        @OptIn(ExperimentalMaterial3Api::class)
        PullToRefreshBox(
            isRefreshing = state.isRefreshing,
            onRefresh = viewModel::refresh,
            modifier = Modifier.weight(1f)
        ) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                state.error != null -> {
                    ErrorContent(
                        message = state.error,
                        onRetry = viewModel::loadBooks
                    )
                }

                else -> {
                    BookList(
                        books = state.books,
                        onBookClick = { book -> onBookClick(book.id) }
                    )
                }
            }
        }
    }
}