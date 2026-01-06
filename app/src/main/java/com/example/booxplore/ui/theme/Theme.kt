package com.example.booxplore.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import com.example.booxplore.data.local.AppTheme

private val DefaultDarkScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val DefaultLightScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
)

private val OceanDarkScheme = darkColorScheme(
    primary = OceanPrimaryDark,
    secondary = OceanSecondaryDark,
    tertiary = OceanTertiaryDark
)

private val OceanLightScheme = lightColorScheme(
    primary = OceanPrimaryLight,
    secondary = OceanSecondaryLight,
    tertiary = OceanTertiaryLight
)

private val NatureDarkScheme = darkColorScheme(
    primary = NaturePrimaryDark,
    secondary = NatureSecondaryDark,
    tertiary = NatureTertiaryDark
)

private val NatureLightScheme = lightColorScheme(
    primary = NaturePrimaryLight,
    secondary = NatureSecondaryLight,
    tertiary = NatureTertiaryLight
)

@Composable
fun BooxploreTheme(
    darkTheme: Boolean, 
    currentTheme: AppTheme = AppTheme.DEFAULT, 
    dynamicColor: Boolean = false, 
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        currentTheme == AppTheme.OCEAN -> if (darkTheme) OceanDarkScheme else OceanLightScheme
        currentTheme == AppTheme.NATURE -> if (darkTheme) NatureDarkScheme else NatureLightScheme
        else -> if (darkTheme) DefaultDarkScheme else DefaultLightScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}