package com.isaka.todoapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.isaka.todoapp.R

// Set of Material typography styles to start with
private val Poppins = FontFamily(
    Font(R.font.poppins_regular),
    Font(R.font.poppins_italic,  FontWeight.Normal, FontStyle.Italic),
    Font(R.font.poppins_semi_bold, FontWeight.SemiBold),
    Font(R.font.poppins_semi_bold_italic, FontWeight.SemiBold, FontStyle.Italic),
    Font(R.font.poppins_bold, FontWeight.Bold),
    Font(R.font.poppins_bold_italic, FontWeight.Bold, FontStyle.Italic),
    Font(R.font.poppins_black, FontWeight.Black),
    Font(R.font.poppins_black_italic, FontWeight.Black, FontStyle.Italic),
    Font(R.font.poppins_extra_bold, FontWeight.ExtraBold),
    Font(R.font.poppins_extra_bold_italic, FontWeight.ExtraBold, FontStyle.Italic),
    Font(R.font.poppins_light, FontWeight.Light),
    Font(R.font.poppins_light_italic, FontWeight.Light, FontStyle.Italic),
    Font(R.font.poppins_extra_light, FontWeight.ExtraLight),
    Font(R.font.poppins_extra_light_italic, FontWeight.ExtraLight, FontStyle.Italic),
    Font(R.font.poppins_medium, FontWeight.Medium),
    Font(R.font.poppins_medium_italic, FontWeight.Medium, FontStyle.Italic),
    Font(R.font.poppins_thin, FontWeight.Thin),
    Font(R.font.poppins_thin_italic, FontWeight.Thin, FontStyle.Italic)
)

private val defaultTypography = Typography()
val Typography = Typography(
    displayLarge = defaultTypography.displayLarge.copy(fontFamily = Poppins),
    displayMedium = defaultTypography.displayMedium.copy(fontFamily = Poppins),
    displaySmall = defaultTypography.displaySmall.copy(fontFamily = Poppins),

    headlineLarge = defaultTypography.headlineLarge.copy(fontFamily = Poppins),
    headlineMedium = defaultTypography.headlineMedium.copy(fontFamily = Poppins),
    headlineSmall = defaultTypography.headlineSmall.copy(fontFamily = Poppins),

    titleLarge = defaultTypography.titleLarge.copy(fontFamily = Poppins),
    titleMedium = defaultTypography.titleMedium.copy(fontFamily = Poppins),
    titleSmall = defaultTypography.titleSmall.copy(fontFamily = Poppins),

    bodyLarge = defaultTypography.bodyLarge.copy(fontFamily = Poppins),
    bodyMedium = defaultTypography.bodyMedium.copy(fontFamily = Poppins),
    bodySmall = defaultTypography.bodySmall.copy(fontFamily = Poppins),

    labelLarge = defaultTypography.labelLarge.copy(fontFamily = Poppins),
    labelMedium = defaultTypography.labelMedium.copy(fontFamily = Poppins),
    labelSmall = defaultTypography.labelSmall.copy(fontFamily = Poppins)
)
