package com.digitalcash.calendar.utils

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.staticCompositionLocalOf

object Constants {
    val LocalSnackBarHostProvider =
        staticCompositionLocalOf<SnackbarHostState?> { null }
}