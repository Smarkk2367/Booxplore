package com.example.booxplore.ui.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.example.booxplore.data.local.ThemeManager
import com.example.booxplore.data.local.AppTheme

@Composable
fun SettingsScreen(
    themeManager: ThemeManager
) {
    val isDarkTheme by themeManager.isDarkTheme.collectAsState()
    val currentTheme by themeManager.currentTheme.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Display Settings",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { themeManager.toggleDarkMode() }
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Dark Mode",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f)
            )
            Switch(
                checked = isDarkTheme,
                onCheckedChange = { themeManager.toggleDarkMode() }
            )
        }
        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
        Text(
            text = "App Theme",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        AppTheme.values().forEach { theme ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (theme == currentTheme),
                        onClick = { themeManager.setTheme(theme) },
                        role = Role.RadioButton
                    )
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (theme == currentTheme),
                    onClick = null
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = theme.name.lowercase().capitalize(),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

// Helper extension for capitalizing enum names
private fun String.capitalize(): String {
    return this.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
}
