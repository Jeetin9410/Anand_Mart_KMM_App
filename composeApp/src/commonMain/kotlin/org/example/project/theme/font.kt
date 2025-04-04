package org.example.project.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.dancing_script_regular
import kotlinproject.composeapp.generated.resources.inter_bold
import kotlinproject.composeapp.generated.resources.inter_extra_light
import kotlinproject.composeapp.generated.resources.inter_light
import kotlinproject.composeapp.generated.resources.inter_medium_italic
import kotlinproject.composeapp.generated.resources.inter_regular
import kotlinproject.composeapp.generated.resources.poppins_bd
import kotlinproject.composeapp.generated.resources.poppins_lt
import kotlinproject.composeapp.generated.resources.poppins_med
import kotlinproject.composeapp.generated.resources.poppins_rg

import org.jetbrains.compose.resources.Font

@Composable
fun interFontFamily(): FontFamily {
    return FontFamily(
        Font(Res.font.inter_extra_light, FontWeight.ExtraLight),
        Font(Res.font.poppins_lt, FontWeight.Light),
        Font(Res.font.poppins_rg, FontWeight.Normal),
        Font(Res.font.poppins_bd, FontWeight.Bold),
        Font(Res.font.poppins_med, FontWeight.Medium),


    )
}
