package com.smitcoderx.scratch.ui.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.smitcoderx.scratch.R

object Fonts {
    val lexendFamily = FontFamily(
        Font(R.font.lexend_bold, weight = FontWeight.Bold),
        Font(R.font.lexend_regular, weight = FontWeight.Normal),
        Font(R.font.lexend_black, weight = FontWeight.Black),
        Font(R.font.lexend_light, weight = FontWeight.Light),
        Font(R.font.lexend_semi_bold, weight = FontWeight.SemiBold),
        Font(R.font.lexend_medium, weight = FontWeight.Medium),
    )
}
