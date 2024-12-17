package com.cjmobileapps.quidditch_players_kmm_2024.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// TODO test this
private val DarkColorScheme =
    darkColorScheme(
        primary = QuidditchPlayersBlack,
        secondary = QuidditchPlayersBlack,
        tertiary = QuidditchPlayersBlack,
        surface = QuidditchPlayersBlack,
        onPrimary = QuidditchPlayersGreen,
    )

private val LightColorScheme =
    lightColorScheme(
        // A primary color is the color displayed most frequently across your app's screens and components.
        primary = QuidditchPlayersBlack,
        // A secondary color provides more ways to accent and distinguish your product.
        secondary = QuidditchPlayersGreen,
        // The tertiary key color is used to derive the roles of contrasting
        // accents that can be used to balance primary and secondary colors or bring enhanced attention to an element.
        tertiary = QuidditchPlayersBlackDark,
        // The background color appears behind scrollable content.
        background = Color.LightGray,
        // Surface colors affect the surfaces of components.
        surface = Color.White,
        onPrimary = Color.White,
        onSecondary = Color.Black,
        onTertiary = Color.White,
        onBackground = QuidditchPlayersBlack,
        onSurface = QuidditchPlayersBlack,
    )

@Composable
fun QuidditchPlayersKMM2024Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme


    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
    )
}
