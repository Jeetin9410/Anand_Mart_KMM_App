package org.example.project.theme.typography

import androidx.compose.runtime.Composable
import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.example.project.theme.interFontFamily

@Composable
fun appTypography(): Typography {
    val interFont = interFontFamily()

    return Typography(
        h1 = TextStyle(
            fontFamily = interFont,
            fontWeight = FontWeight.Light,
            fontSize = 96.sp,
            letterSpacing = (-1.5).sp
        ),
        h2 = TextStyle(
            fontFamily = interFont,
            fontWeight = FontWeight.Light,
            fontSize = 60.sp,
            letterSpacing = (-0.5).sp
        ),
        h3 = TextStyle(
            fontFamily = interFont,
            fontWeight = FontWeight.Normal,
            fontSize = 48.sp
        ),
        h4 = TextStyle(
            fontFamily = interFont,
            fontWeight = FontWeight.Normal,
            fontSize = 34.sp,
            letterSpacing = 0.25.sp
        ),
        h5 = TextStyle(
            fontFamily = interFont,
            fontWeight = FontWeight.Normal,
            fontSize = 24.sp
        ),
        h6 = TextStyle(
            fontFamily = interFont,
            fontWeight = FontWeight.Normal,
            fontSize = 20.sp,
            letterSpacing = 0.15.sp
        ),
        subtitle1 = TextStyle(
            fontFamily = interFont,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            letterSpacing = 0.15.sp
        ),
        subtitle2 = TextStyle(
            fontFamily = interFont,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            letterSpacing = 0.1.sp
        ),
        body1 = TextStyle(
            fontFamily = interFont,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            letterSpacing = 0.5.sp
        ),
        body2 = TextStyle(
            fontFamily = interFont,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            letterSpacing = 0.25.sp
        ),
        button = TextStyle(
            fontFamily = interFont,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            letterSpacing = 1.25.sp
        ),
        caption = TextStyle(
            fontFamily = interFont,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            letterSpacing = 0.4.sp
        ),
        overline = TextStyle(
            fontFamily = interFont,
            fontWeight = FontWeight.Normal,
            fontSize = 10.sp,
            letterSpacing = 1.5.sp
        )
    )
}
