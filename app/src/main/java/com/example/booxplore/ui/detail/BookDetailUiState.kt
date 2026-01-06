package com.example.booxplore.ui.detail

import com.example.booxplore.data.domain.model.Book

data class BookDetailUiState(
    val isLoading: Boolean = false,
    val book: Book? = null,
    val error: String? = null,
    val isFavorite: Boolean = false
)
