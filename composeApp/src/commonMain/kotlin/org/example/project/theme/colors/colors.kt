package org.example.project.theme.colors

import androidx.compose.ui.graphics.Color

object AppColors {
    // App Basic Colors
    val primary = Color(0xFF4b68ff)
    val secondary = Color(0xFFFFE24B)
    val accent = Color(0xFFb0c7ff)

    val selected = Color(0xFF1565C0)
    val unselected = Color(0xFFC4C4C4)

    // Text Colors
    val textPrimary = Color(0xFF333333)
    val textSecondary = Color(0xFF6C757D)
    val textWhite = Color.White

    // Background Colors
    val light = Color(0xFFF6F6F6)
    val dark = Color(0xFF272727)
    val primaryBackground = Color(0xFFF3F5FF)

    // Background Container colors
    val lightContainer = Color(0xFFF6F6F6)
    val darkContainer = Color.White.copy(alpha = 0.1f)

    // Button Colors
    val buttonPrimary = Color(0xFF4b68ff)
    val buttonSecondary = Color(0xFF6C757D)
    val buttonDisabled = Color(0xFFC4C4C4)

    // Border Colors
    val borderPrimary = Color(0xFFD9D9D9)
    val borderSecondary = Color(0xFFE6E6E6) // Fixed from 0xFFE666 to 6-digit format

    // Error and Validation Colors
    val error = Color(0xFFD32F2F)
    val success = Color(0xFF388E3C)
    val warning = Color(0xFFF57C00)
    val info = Color(0xFF1976D2)

    // Neutral Shades
    val black = Color(0xFF232323)
    val darkerGrey = Color(0xFF4F4F4F)
    val darkGrey = Color(0xFF939393)
    val grey = Color(0xFFE0E0E0)
    val softGrey = Color(0xFF4F4F4F) // Duplicate of darkerGrey?
    val lightGrey = Color(0xFFF9F9F9)
    val white = Color.White
}