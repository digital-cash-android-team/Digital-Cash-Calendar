package com.digitalcash.split.ui.main.calendar.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.digitalcash.calendar.ui.CalendarDialog
import com.digitalcash.calendar.ui.WheelMonthYearPicker
import com.digitalcash.calendar.ui.noRippleEffect
import com.digitalcash.calendar.utils.CalendarDateType
import com.digitalcash.calendar.utils.CalendarDialogColorConfig
import com.digitalcash.calendar.utils.CalendarDialogDimensionConfig
import com.digitalcash.calendar.utils.CalendarDialogTextConfig
import com.digitalcash.calendar.utils.CalendarDialogTextStyleConfig
import com.digitalcash.calendar.utils.CalendarState
import com.digitalcash.calendar.utils.CalendarViewType
import com.github.msarhan.ummalqura.calendar.UmmalquraCalendar
import java.time.LocalDate
import java.util.Calendar

@Composable
fun defaultColorConfig(): CalendarDialogColorConfig = CalendarDialogColorConfig(
    selectedDayColor = MaterialTheme.colorScheme.primary,
    availableDayColor = MaterialTheme.colorScheme.surfaceContainerHigh,
    unAvailableDayColor = MaterialTheme.colorScheme.surface,
    selectedMonthColor = MaterialTheme.colorScheme.primary,
    unSelectedMonthColor = MaterialTheme.colorScheme.surfaceContainerHigh,
    selectedYearColor = MaterialTheme.colorScheme.primary,
    unSelectedYearColor = MaterialTheme.colorScheme.surfaceContainerHigh,
    dialogBackgroundColor = MaterialTheme.colorScheme.surface,
)

@Composable
fun DgCalendar(
    calendarState: CalendarState = CalendarState(),
    onDateSelected: ((LocalDate) -> Unit)? = null,
    onMultiSelected: ((List<LocalDate>) -> Unit)? = null,
    onRangeSelected: ((LocalDate, LocalDate) -> Unit)? = null,
    onDismissRequest: () -> Unit,
    textConfig: CalendarDialogTextConfig = CalendarDialogTextConfig(),
    colorConfig: CalendarDialogColorConfig = defaultColorConfig(),
    dimensionConfig: CalendarDialogDimensionConfig = CalendarDialogDimensionConfig(),
    textStyleConfig: CalendarDialogTextStyleConfig = CalendarDialogTextStyleConfig(),
    positiveDialogButtonContent: @Composable () -> Unit = {
        Text(
            textConfig.selectButtonText,
            style = textStyleConfig.yearHeaderTextStyle,
        )
    },
    negativeDialogButtonContent: @Composable () -> Unit = {
        Text(
            textConfig.cancelButtonText,
            style = textStyleConfig.yearHeaderTextStyle,
        )
    },
) {
    val selectedDate = calendarState.selectedDate

    val currentCalendar = remember(calendarState) {
        when (calendarState.calendarDateType) {
            CalendarDateType.HIJRI -> {
                if (calendarState.selectedDate.year in 1301..1499)
                    UmmalquraCalendar().apply {
                        set(Calendar.YEAR, selectedDate.year)
                        set(Calendar.MONTH, selectedDate.monthValue - 1)
                        set(Calendar.DAY_OF_MONTH, selectedDate.dayOfMonth)
                    }
                else UmmalquraCalendar().apply {
                    set(Calendar.YEAR, this.get(UmmalquraCalendar.YEAR))
                    set(Calendar.MONTH, this.get(UmmalquraCalendar.MONTH) + 1)
                    set(Calendar.DAY_OF_MONTH, this.get(UmmalquraCalendar.DAY_OF_MONTH))
                }
            }

            CalendarDateType.GREGORIAN -> {
                Calendar.getInstance().apply {
                    set(Calendar.YEAR, selectedDate.year)
                    set(Calendar.MONTH, selectedDate.monthValue - 1)
                    set(Calendar.DAY_OF_MONTH, selectedDate.dayOfMonth)
                }
            }
        }
    }

    val maxDate = remember(LocalDate.now()) {
        when (calendarState.calendarDateType) {
            CalendarDateType.HIJRI -> {
                if (calendarState.selectedDate.year in 1301..1499)
                    UmmalquraCalendar().apply {
                        set(Calendar.YEAR, LocalDate.now().year)
                        set(Calendar.MONTH, LocalDate.now().monthValue - 1)
                        set(Calendar.DAY_OF_MONTH, LocalDate.now().dayOfMonth)
                    }
                else UmmalquraCalendar().apply {
                    set(Calendar.YEAR, this.get(UmmalquraCalendar.YEAR))
                    set(Calendar.MONTH, this.get(UmmalquraCalendar.MONTH) + 1)
                    set(Calendar.DAY_OF_MONTH, this.get(UmmalquraCalendar.DAY_OF_MONTH))
                }
            }

            CalendarDateType.GREGORIAN -> {
                Calendar.getInstance().apply {
                    set(Calendar.YEAR, LocalDate.now().year)
                    set(Calendar.MONTH, LocalDate.now().monthValue - 1)
                    set(Calendar.DAY_OF_MONTH, LocalDate.now().dayOfMonth)
                }
            }
        }
    }

    val currentDay = currentCalendar.get(Calendar.DAY_OF_MONTH)
    val currentMonth = currentCalendar.get(Calendar.MONTH)
    val currentYear = currentCalendar.get(Calendar.YEAR)

    val selectedDay by remember { mutableIntStateOf(currentDay) }
    val selectedMonth by remember { mutableIntStateOf(currentMonth) }
    val selectedYear by remember { mutableIntStateOf(currentYear) }


    when (calendarState.calendarViewType) {
        CalendarViewType.Dialog -> {
            CalendarDialog(
                maxDayOfMonth = maxDate.get(Calendar.DAY_OF_MONTH),
                maxMonth = maxDate.get(Calendar.MONTH),
                maxYear = maxDate.get(Calendar.YEAR),
                selectedYear = selectedYear,
                selectedMonth = selectedMonth,
                selectedDay = selectedDay,
                yearsRange = if (calendarState.calendarDateType == CalendarDateType.HIJRI && calendarState.range.last >= 1499)
                    calendarState.range.first..selectedYear
                else calendarState.range,
                onDateSelected = { year, month, day ->
                    onDateSelected?.let {
                        it(
                            LocalDate.of(
                                year,
                                month,
                                day,
                            ),
                        )
                    }
                },
                onRangeSelected = { firstDate, secondDate ->
                    onRangeSelected?.let {
                        it(firstDate, secondDate)
                    }
                },
                onMultiSelected = { datesList ->
                    onMultiSelected?.let {
                        it(datesList)
                    }
                },
                onDismissRequest = onDismissRequest,
                calendarDateType = calendarState.calendarDateType,
                disableFutureDates = calendarState.disableFutureDates,
                enableRangeSelect = calendarState.enableRangeSelect,
                enableMultiSelect = calendarState.enableMultiSelect,
                textConfig = textConfig,
                textStyleConfig = textStyleConfig,
                colorConfig = colorConfig,
                dimensionConfig = dimensionConfig,
                language = "en",
                confirmButtonContent = positiveDialogButtonContent,
                cancelButtonContent = negativeDialogButtonContent,
            )
        }

        CalendarViewType.DropDown -> {
            DropDownCalendar(
                maxDayOfMonth = maxDate.get(Calendar.DAY_OF_MONTH),
                maxMonth = maxDate.get(Calendar.MONTH),
                maxYear = maxDate.get(Calendar.YEAR),
                selectedYear = selectedYear,
                selectedDay = selectedDay,
                selectedMonth = selectedMonth,
                language = "ar",
                calendarState = calendarState,
            )
        }

        CalendarViewType.PICKER -> {
            var showBottomSheet by remember {
                mutableStateOf(true)
            }
            WheelMonthYearPicker(
                modifier = Modifier,
                maxDayOfMonth = maxDate.get(Calendar.DAY_OF_MONTH),
                maxMonth = maxDate.get(Calendar.MONTH),
                maxYear = maxDate.get(Calendar.YEAR),
                selectedYear = selectedYear,
                selectedDay = selectedDay,
                selectedMonth = selectedMonth,
                language = "en",
                calendarState = calendarState,
                onDismiss = onDismissRequest,
                showBottomSheet = showBottomSheet,
                size = DpSize(256.dp, 250.dp)
            ) { date ->
                Box(Modifier.fillMaxWidth()) {
                    Text(
                        text = "title",
                        modifier = Modifier.align(Alignment.Center)
                    )
                    Text(
                        text = "doneLabel",
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .align(Alignment.CenterEnd)
                            .noRippleEffect {
//                                scope.launch { modalBottomSheetState.bottomSheetState.partialExpand() }
//                                isModalSheetVisible = false
                                showBottomSheet = false
                                println("selecred dasd $date")
                            }
                    )
                }
            }
        }
    }
}