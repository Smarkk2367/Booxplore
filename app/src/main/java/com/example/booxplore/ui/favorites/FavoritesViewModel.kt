package com.example.booxplore.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booxplore.data.domain.model.Book
import com.example.booxplore.data.local.FavoritesManager
import com.example.booxplore.data.repository.BookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val favoritesManager: FavoritesManager,
    private val repository: BookRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FavoritesUiState())
    val uiState: StateFlow<FavoritesUiState> = _uiState.asStateFlow()

    init {
        observeFavorites()
    }

    private fun observeFavorites() {
        viewModelScope.launch {
            favoritesManager.favoritesFlow.collect { favoriteIds ->
                if (favoriteIds.isEmpty()) {
                    _uiState.update { it.copy(books = emptyList(), isLoading = false) }
                } else {
                    loadBooks(favoriteIds)
                }
            }
        }
    }

    private suspend fun loadBooks(ids: Set<String>) {
        _uiState.update { it.copy(isLoading = true) }
        val loadedBooks = mutableListOf<Book>()
        

        ids.forEach { id ->
            repository.getBookDetails(id)
                .onSuccess { loadedBooks.add(it) }
                .onFailure {
                    //placeholder
                }
        }
        
        _uiState.update { it.copy(books = loadedBooks, isLoading = false) }
    }
}
