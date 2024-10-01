package com.digitalcash.calendar.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.digitalcash.calendar.utils.CalenderUtils.poppinsRegular

/**
 * Configuration For Calendar Dialog Texts.
 */
data class CalendarDialogTextConfig(
    val selectButtonText: String = "Select",
    val cancelButtonText: String = "Cancel",
    val arrowBackDescription: String = "Previous Month",
    val arrowForwardDescription: String = "Next Month",
    val selectYearDescription: String = "Select Year",
)

/**
 * Configuration For Calendar Dialog Text Styles.
 */
data class CalendarDialogTextStyleConfig(
    val yearHeaderTextStyle: TextStyle = TextStyle(
        color = Color.Black,
        fontFamily = poppinsRegular,
    ),
    val selectedDayTextStyle: TextStyle = TextStyle(
        color = Color.White,
        fontFamily = poppinsRegular,
    ),
    val unselectedDayTextStyle: TextStyle = TextStyle(
        color = Color.Gray,
        fontFamily = poppinsRegular,
    ),
    val availableDayTextStyle: TextStyle = TextStyle(
        color = Color.Black,
        fontFamily = poppinsRegular,
    ),
    val selectedMonthTextStyle: TextStyle = TextStyle(
        color = Color.White,
        fontFamily = poppinsRegular,
    ),
    val unselectedMonthTextStyle: TextStyle = TextStyle(
        color = Color.Black,
        fontFamily = poppinsRegular,
    ),
    val selectedYearTextStyle: TextStyle = TextStyle(
        color = Color.White,
        fontFamily = poppinsRegular,
    ),
    val unselectedYearTextStyle: TextStyle = TextStyle(
        color = Color.Black,
        fontFamily = poppinsRegular,
    ),
    val yearHeaderStyle: TextStyle = TextStyle(
        color = Color.Black,
        fontFamily = poppinsRegular,
    ),
)


/**
 * Configuration For Calendar Dialog Colors.
 */
data class CalendarDialogColorConfig(
    val selectedDayColor: Color = Color.Red,
    val availableDayColor: Color = Color.White,
    val unAvailableDayColor: Color = Color.White,
    val selectedMonthColor: Color = Color.White,
    val unSelectedMonthColor: Color = Color.White,
    val selectedYearColor: Color = Color.White,
    val unSelectedYearColor: Color = Color.White,
    val dialogBackgroundColor: Color = Color.White,
)

/**
 * Configuration For Calendar Dialog Dimensions.
 */
data class CalendarDialogDimensionConfig(
    val dialogPadding: Dp = 16.dp,
    val buttonPadding: Dp = 8.dp,
    val iconSize: Dp = 24.dp,
    val dayBoxSize: Dp = 40.dp,
    val dayBoxCornerRadius: Dp = 25.dp,
    val spacerHeight: Dp = 16.dp,
    val spacerWidth: Dp = 16.dp,
    val daysFlowRowSpacing: Dp = 5.dp,
    val monthsFlowRowSpacing: Dp = 10.dp,
    val yearsFlowRowSpacing: Dp = 8.dp,
)