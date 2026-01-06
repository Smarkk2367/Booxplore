package com.example.booxplore.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booxplore.data.local.FavoritesManager
import com.example.booxplore.data.repository.BookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BookDetailViewModel(
    private val repository: BookRepository,
    private val favoritesManager: FavoritesManager,
    private val bookId: String
) : ViewModel() {

    private val _uiState = MutableStateFlow(BookDetailUiState(isLoading = true))
    val uiState: StateFlow<BookDetailUiState> = _uiState.asStateFlow()

    init {
        loadDetails()
        observeFavorites()
    }

    private fun loadDetails() {
         viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            repository.getBookDetails(bookId)
                .onSuccess { book ->
                    _uiState.update { it.copy(book = book, isLoading = false) }
                }
                .onFailure {
                    _uiState.update { it.copy(error = "Couldn't load book details", isLoading = false) }
                }
        }
    }

    private fun observeFavorites() {
        viewModelScope.launch {
            favoritesManager.favoritesFlow.collect { favorites ->
                _uiState.update { it.copy(isFavorite = favorites.contains(bookId)) }
            }
        }
    }

    fun toggleFavorite() {
        if (favoritesManager.isFavorite(bookId)) {
            favoritesManager.removeFavorite(bookId)
        } else {
            favoritesManager.addFavorite(bookId)
        }
    }
}