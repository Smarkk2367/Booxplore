package com.example.booxplore.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booxplore.data.repository.BookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: BookRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadBooks()
    }

    fun loadBooks() {
        viewModelScope.launch {
            _uiState.value = HomeUiState(isLoading = true)

            repository.getFictionBooks()
                .onSuccess { books ->
                    _uiState.value = HomeUiState(
                        books = books,
                        isLoading = false
                    )
                }
                .onFailure {
                    _uiState.value = HomeUiState(
                        isLoading = false,
                        error = "Couldn't load data"
                    )
                }
        }
    }

    fun searchBooks(query: String) {
        if (query.isBlank()) {
            loadBooks()
            return
        }
        
        viewModelScope.launch {
            _uiState.value = HomeUiState(isLoading = true)
            
            repository.searchBooks(query)
                .onSuccess { books ->
                    _uiState.value = HomeUiState(
                        books = books,
                        isLoading = false
                    )
                }
                .onFailure {
                    _uiState.value = HomeUiState(
                        isLoading = false,
                        error = "Search failed"
                    )
                }
        }
    }
}