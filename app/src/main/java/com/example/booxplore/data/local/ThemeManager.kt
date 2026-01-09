package com.example.booxplore.data.local

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

enum class AppTheme {
    DEFAULT, OCEAN, NATURE
}

class ThemeManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("booxplore_theme", Context.MODE_PRIVATE)

    private val _isDarkTheme = MutableStateFlow(prefs.getBoolean("is_dark_mode", false))
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme.asStateFlow()

    private val _currentTheme = MutableStateFlow(
        AppTheme.valueOf(prefs.getString("app_theme", AppTheme.DEFAULT.name) ?: AppTheme.DEFAULT.name)
    )
    val currentTheme: StateFlow<AppTheme> = _currentTheme.asStateFlow()

    fun toggleDarkMode() {
        val newSetting = !_isDarkTheme.value
        _isDarkTheme.value = newSetting
        prefs.edit().putBoolean("is_dark_mode", newSetting).apply()
    }

    fun setTheme(theme: AppTheme) {
        _currentTheme.value = theme
        prefs.edit().putString("app_theme", theme.name).apply()
    }
}
