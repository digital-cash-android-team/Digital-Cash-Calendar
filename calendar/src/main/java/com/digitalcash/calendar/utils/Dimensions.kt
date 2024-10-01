package com.digitalcash.calendar.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


open class Dimensions {
    val default: Dp = 0.dp
    val tiny: Dp = 2.dp
    val spaceVerySmall: Dp = 5.dp
    val spaceSmall: Dp = 8.dp
    val spaceExtraSmall: Dp = 12.dp
    val spaceMedium: Dp = 16.dp
    val spaceExtraMedium: Dp = 20.dp
    val spaceMediumLarge: Dp = 30.dp
    val spaceLarge: Dp = 38.dp
    val spaceXLarge: Dp = 58.dp
    val spaceXXLarge: Dp = 64.dp
    val spaceXXXLarge: Dp = 100.dp
    val spaceGiant: Dp = 120.dp

    val circularCornerRadius: Dp = 50.dp
    val buttonHeight: Dp = 60.dp
    val dividerHeight = 1.dp
}

/**
 * font scale aware extension
 */
val Number.Sp
    @Composable
    get() = (this.toInt() / LocalDensity.current.fontScale).sp

/**
 * size scale aware extension
 */
val Number.Dp
    @Composable
    get() = (this.toInt() / LocalDensity.current.density).dp

@Composable
fun Int.pxToDp() = with(LocalDensity.current) { this@pxToDp.toDp() }

val LocalSpacing = compositionLocalOf { Dimensions() }