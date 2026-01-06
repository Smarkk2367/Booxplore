package com.example.booxplore.data.local

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FavoritesManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("booxplore_favorites", Context.MODE_PRIVATE)
    private val _favoritesFlow = MutableStateFlow<Set<String>>(getFavorites())

    val favoritesFlow: StateFlow<Set<String>> = _favoritesFlow.asStateFlow()

    fun addFavorite(id: String) {
        val current = getFavorites().toMutableSet()
        current.add(id)
        saveFavorites(current)
    }

    fun removeFavorite(id: String) {
        val current = getFavorites().toMutableSet()
        current.remove(id)
        saveFavorites(current)
    }

    fun isFavorite(id: String): Boolean {
        return getFavorites().contains(id)
    }

    fun getFavorites(): Set<String> {
        return prefs.getStringSet("favorites_ids", emptySet()) ?: emptySet()
    }

    private fun saveFavorites(ids: Set<String>) {
        prefs.edit().putStringSet("favorites_ids", ids).apply()
        _favoritesFlow.value = ids
    }
}
