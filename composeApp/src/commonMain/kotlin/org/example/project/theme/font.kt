package org.example.project.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.inter_bold
import kotlinproject.composeapp.generated.resources.inter_extra_light
import kotlinproject.composeapp.generated.resources.inter_light
import kotlinproject.composeapp.generated.resources.inter_medium_italic
import kotlinproject.composeapp.generated.resources.inter_regular

import org.jetbrains.compose.resources.Font

@Composable
fun interFontFamily(): FontFamily {
    return FontFamily(
        Font(Res.font.inter_extra_light, FontWeight.ExtraLight),
        Font(Res.font.inter_light, FontWeight.Light),
        Font(Res.font.inter_regular, FontWeight.Normal),
        Font(Res.font.inter_bold, FontWeight.Bold),
        Font(Res.font.inter_medium_italic, FontWeight.Medium),

    )
}
