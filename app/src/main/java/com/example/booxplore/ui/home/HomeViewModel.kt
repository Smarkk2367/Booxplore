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

    private var currentQuery: String? = null

    init {
        loadBooks()
    }

    fun loadBooks(isRefreshing: Boolean = false) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = !isRefreshing,
                isRefreshing = isRefreshing
            )

            repository.getFictionBooks()
                .onSuccess { books ->
                    _uiState.value = HomeUiState(
                        books = books,
                        isLoading = false,
                        isRefreshing = false
                    )
                    currentQuery = null
                }
                .onFailure {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        isRefreshing = false,
                        error = "Couldn't load data"
                    )
                }
        }
    }

    fun searchBooks(query: String, isRefreshing: Boolean = false) {
        if (query.isBlank()) {
            loadBooks(isRefreshing)
            return
        }
        
        currentQuery = query
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = !isRefreshing,
                isRefreshing = isRefreshing
            )
            
            repository.searchBooks(query)
                .onSuccess { books ->
                    _uiState.value = HomeUiState(
                        books = books,
                        isLoading = false,
                        isRefreshing = false
                    )
                }
                .onFailure {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        isRefreshing = false,
                        error = "Search failed"
                    )
                }
        }
    }

    fun refresh() {
        val query = currentQuery
        if (query != null) {
            searchBooks(query, isRefreshing = true)
        } else {
            loadBooks(isRefreshing = true)
        }
    }
}