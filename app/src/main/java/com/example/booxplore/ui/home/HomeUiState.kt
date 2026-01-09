package com.example.booxplore.ui.home

import com.example.booxplore.data.domain.model.Book

data class HomeUiState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val books: List<Book> = emptyList(),
    val error: String? = null
)