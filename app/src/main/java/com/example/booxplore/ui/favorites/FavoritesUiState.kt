package com.example.booxplore.ui.favorites

import com.example.booxplore.data.domain.model.Book

data class FavoritesUiState(
    val books: List<Book> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
