//package com.digitalcash.split.ui.main.calendar.sample
//
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableIntStateOf
//import androidx.compose.runtime.remember
//import com.digitalcash.split.ui.main.calendar.ui.CalenderDialog
//import com.digitalcash.calendar.utils.CalendarDateType
//import com.digitalcash.calendar.utils.CalendarDialogColorConfig
//import com.digitalcash.calendar.utils.CalendarDialogDimensionConfig
//import com.digitalcash.calendar.utils.CalendarDialogTextConfig
//import com.digitalcash.calendar.utils.CalendarDialogTextStyleConfig
//import com.digitalcash.calendar.utils.CalendarState
//import java.time.LocalDate
//
//@Composable
//fun CalendarDialogSample(
//    selectedYear: Int,
//    selectedMonth: Int,
//    selectedDay: Int,
//    calendarState: CalendarState = CalendarState(),
//    onSelectedDate: (LocalDate) -> Unit,
//    textConfig: CalendarDialogTextConfig = CalendarDialogTextConfig(),
//    colorConfig: CalendarDialogColorConfig = CalendarDialogColorConfig(),
//    dimensionConfig: CalendarDialogDimensionConfig = CalendarDialogDimensionConfig(),
//    textStyleConfig: CalendarDialogTextStyleConfig = CalendarDialogTextStyleConfig(),
//    positiveDialogButtonContent: @Composable () -> Unit = {
//        Text(
//            textConfig.selectButtonText,
//            style = textStyleConfig.yearHeaderTextStyle,
//        )
//    },
//    negativeDialogButtonContent: @Composable () -> Unit = {
//        Text(
//            textConfig.cancelButtonText,
//            style = textStyleConfig.yearHeaderTextStyle,
//        )
//    },
//) {
//    val currentYear by remember { mutableIntStateOf(selectedYear) }
//    val currentMonth by remember { mutableIntStateOf(selectedMonth) }
//    val currentDay by remember { mutableIntStateOf(selectedDay) }
//
//    val selectedDay1 by remember { mutableIntStateOf(currentDay) }
//    val selectedMonth1 by remember { mutableIntStateOf(currentMonth) }
//    val selectedYear1 by remember { mutableIntStateOf(currentYear) }
//
//
//    CalenderDialog(
//        selectedYear = selectedYear1,
//        selectedMonth = selectedMonth1,
//        selectedDay = selectedDay1,
//        yearsRange = if (calendarState.calendarDateType == CalendarDateType.HIJRI && calendarState.range.last >= 1499)
//            calendarState.range.first..selectedYear
//        else calendarState.range,
//        onDateSelected = { year, month, day ->
//            onSelectedDate(LocalDate.of(year, month + 1, day))
//        },
//        onDismissRequest = {},
//        calendarDateType = calendarState.calendarDateType,
//        disableFutureDates = calendarState.disableFutureDates,
//        textConfig = textConfig,
//        textStyleConfig = textStyleConfig,
//        colorConfig = colorConfig,
//        dimensionConfig = dimensionConfig,
//        confirmButtonContent = positiveDialogButtonContent,
//        cancelButtonContent = negativeDialogButtonContent,
//    )
//}

//@Composable
//fun CalendarDialogSample2(
//    calendarState: CalendarState = CalendarState(),
//    onSelectedDate: (LocalDate) -> Unit,
//) {
//    val selectedDate = calendarState.selectedDate
//
//    val currentCalendar = remember(calendarState) {
//        when (calendarState.calendarDateType) {
//            CalendarDateType.HIJRI -> {
//                if (calendarState.selectedDate.year in 1301..1499)
//                    UmmalquraCalendar().apply {
//                        set(Calendar.YEAR, selectedDate.year)
//                        set(Calendar.MONTH, selectedDate.monthValue - 1)
//                        set(Calendar.DAY_OF_MONTH, selectedDate.dayOfMonth)
//                    }
//                else UmmalquraCalendar().apply {
//                    set(Calendar.YEAR, this.get(UmmalquraCalendar.YEAR))
//                    set(Calendar.MONTH, this.get(UmmalquraCalendar.MONTH) + 1)
//                    set(Calendar.DAY_OF_MONTH, this.get(UmmalquraCalendar.DAY_OF_MONTH))
//                }
//            }
//
//            CalendarDateType.GREGORIAN -> {
//                Calendar.getInstance().apply {
//                    set(Calendar.YEAR, selectedDate.year)
//                    set(Calendar.MONTH, selectedDate.monthValue - 1)
//                    set(Calendar.DAY_OF_MONTH, selectedDate.dayOfMonth)
//                }
//            }
//        }
//    }
//
//    val currentDay = currentCalendar.get(Calendar.DAY_OF_MONTH)
//    val currentMonth = currentCalendar.get(Calendar.MONTH)
//    val currentYear = currentCalendar.get(Calendar.YEAR)
//
//    val selectedDay by remember { mutableIntStateOf(currentDay) }
//    val selectedMonth by remember { mutableIntStateOf(currentMonth) }
//    val selectedYear by remember { mutableIntStateOf(currentYear) }
//    CalenderDialog(
//        selectedYear = selectedYear,
//        selectedMonth = selectedMonth,
//        selectedDay = selectedDay,
//        yearsRange = if (calendarState.calendarDateType == CalendarDateType.HIJRI && calendarState.range.last >= 1499) calendarState.range.first..currentCalendar.get(
//            Calendar.YEAR,
//        ) else calendarState.range,
//        onDateSelected = { year, month, day ->
//            onSelectedDate(LocalDate.of(year, month + 1, day))
//        },
//        onDismissRequest = {},
//        calendarDateType = calendarState.calendarDateType,
//        disableFutureDates = calendarState.disableFutureDates,
//        textConfig = CalendarDialogTextConfig(
//            selectButtonText = "Confirm",
//            cancelButtonText = "Close",
//            arrowBackDescription = "Back",
//            arrowForwardDescription = "Forward",
//        ),
//        colorConfig = CalendarDialogColorConfig(
//            unSelectedMonthColor = Color(0xFF81D4FA),
//            selectedMonthColor = Color(0xFF0288D1),
//            unSelectedYearColor = Color(0xFF81D4FA),
//            selectedYearColor = Color(0xFF0288D1),
//            dialogBackgroundColor = Color(0xFFFFCDD2),
//        ),
//        dimensionConfig = CalendarDialogDimensionConfig(
//            dialogPadding = 20.dp,
//            buttonPadding = 10.dp,
//            iconSize = 30.dp,
//            dayBoxSize = 50.dp,
//            dayBoxCornerRadius = 10.dp,
//            spacerHeight = 20.dp,
//            spacerWidth = 10.dp,
//            daysFlowRowSpacing = 5.dp,
//        ),
//    )
//}
//
//@Composable
//fun CalendarDialogSample3(
//    calendarState: CalendarState = CalendarState(),
//    onSelectedDate: (LocalDate) -> Unit,
//) {
//    val selectedDate = calendarState.selectedDate
//
//    val currentCalendar = remember(calendarState) {
//        when (calendarState.calendarDateType) {
//            CalendarDateType.HIJRI -> {
//                if (calendarState.selectedDate.year in 1301..1499)
//                    UmmalquraCalendar().apply {
//                        set(Calendar.YEAR, selectedDate.year)
//                        set(Calendar.MONTH, selectedDate.monthValue - 1)
//                        set(Calendar.DAY_OF_MONTH, selectedDate.dayOfMonth)
//                    }
//                else UmmalquraCalendar().apply {
//                    set(Calendar.YEAR, this.get(UmmalquraCalendar.YEAR))
//                    set(Calendar.MONTH, this.get(UmmalquraCalendar.MONTH) + 1)
//                    set(Calendar.DAY_OF_MONTH, this.get(UmmalquraCalendar.DAY_OF_MONTH))
//                }
//            }
//
//            CalendarDateType.GREGORIAN -> {
//                Calendar.getInstance().apply {
//                    set(Calendar.YEAR, selectedDate.year)
//                    set(Calendar.MONTH, selectedDate.monthValue - 1)
//                    set(Calendar.DAY_OF_MONTH, selectedDate.dayOfMonth)
//                }
//            }
//        }
//    }
//
//    val currentDay = currentCalendar.get(Calendar.DAY_OF_MONTH)
//    val currentMonth = currentCalendar.get(Calendar.MONTH)
//    val currentYear = currentCalendar.get(Calendar.YEAR)
//
//    val selectedDay by remember { mutableIntStateOf(currentDay) }
//    val selectedMonth by remember { mutableIntStateOf(currentMonth) }
//    val selectedYear by remember { mutableIntStateOf(currentYear) }
//    CalenderDialog(
//        selectedYear = selectedYear,
//        selectedMonth = selectedMonth,
//        selectedDay = selectedDay,
//        yearsRange = if (calendarState.calendarDateType == CalendarDateType.HIJRI && calendarState.range.last >= 1499) calendarState.range.first..currentCalendar.get(
//            Calendar.YEAR,
//        ) else calendarState.range,
//        onDateSelected = { year, month, day ->
//            onSelectedDate(LocalDate.of(year, month + 1, day))
//        },
//        onDismissRequest = {},
//        calendarDateType = calendarState.calendarDateType,
//        disableFutureDates = calendarState.disableFutureDates,
//        textConfig = CalendarDialogTextConfig(
//            selectButtonText = "Select",
//            cancelButtonText = "Cancel",
//            arrowBackDescription = "Previous",
//            arrowForwardDescription = "Next",
//        ),
//        colorConfig = CalendarDialogColorConfig(
//            selectedDayColor = Color(0xFF212121),
//            unselectedDayColor = Color(0xFF9E9E9E),
//            availableDayColor = Color(0xFFFFFFFF),
//            unSelectedMonthColor = Color(0xFF81D4FA),
//            selectedMonthColor = Color(0xFF0288D1),
//            unSelectedYearColor = Color(0xFF81D4FA),
//            selectedYearColor = Color(0xFF0288D1),
//            dialogBackgroundColor = Color(0xFFF5F5F5),
//        ),
//        dimensionConfig = CalendarDialogDimensionConfig(
//            dialogPadding = 16.dp,
//            buttonPadding = 8.dp,
//            iconSize = 24.dp,
//            dayBoxSize = 40.dp,
//            dayBoxCornerRadius = 20.dp,
//            spacerHeight = 12.dp,
//            spacerWidth = 8.dp,
//            daysFlowRowSpacing = 5.dp,
//        ),
//    )
//}