package com.digitalcash.base.core.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import com.digitalcash.calendar.utils.CalenderUtils.Sp
import com.digitalcash.calendar.utils.CalenderUtils.poppinsBold
import com.digitalcash.calendar.utils.CalenderUtils.poppinsRegular
import com.digitalcash.calendar.utils.CalenderUtils.poppinsSemiBold

@Composable
fun PoppinsTypography() = Typography(
    headlineLarge = TextStyle(
        fontFamily = poppinsSemiBold,
        fontSize = 44.Sp,
        letterSpacing = 0.5.Sp,

        ),
    headlineMedium = TextStyle(
        fontFamily = poppinsSemiBold,
        fontSize = 32.Sp,
        letterSpacing = 0.5.Sp,

        ),
    headlineSmall = TextStyle(
        fontFamily = poppinsSemiBold,
        fontSize = 44.Sp,
        letterSpacing = 0.5.Sp,

        ),

    titleLarge = TextStyle(
        fontFamily = poppinsRegular,
        fontSize = 24.Sp,
        lineHeight = 16.Sp,

        ),
    titleMedium = TextStyle(
        fontFamily = poppinsSemiBold,
        fontSize = 20.Sp,

        ),
    titleSmall = TextStyle(
        fontFamily = poppinsRegular,
        fontSize = 20.Sp,

        ),

    bodyLarge = TextStyle(
        fontFamily = poppinsSemiBold,
        fontSize = 18.Sp,

        ),
    bodyMedium = TextStyle(
        fontFamily = poppinsRegular,
        fontSize = 18.Sp,

        ),
    bodySmall = TextStyle(
        fontFamily = poppinsRegular,
        fontSize = 16.Sp,

        ),

    labelLarge = TextStyle(
        fontFamily = poppinsBold,
        fontSize = 16.Sp,

        ),
    labelMedium = TextStyle(
        fontFamily = poppinsRegular,
        fontSize = 14.Sp,
    ),

    labelSmall = TextStyle(
        fontFamily = poppinsSemiBold,
        fontSize = 11.Sp,

        ),
)