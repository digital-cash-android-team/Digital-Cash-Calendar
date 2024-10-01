package com.digitalcash.calendar.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.digitalcash.calendar.utils.CalendarDateType
import com.digitalcash.calendar.utils.CalendarDialogColorConfig
import com.digitalcash.calendar.utils.CalendarDialogDimensionConfig
import com.digitalcash.calendar.utils.CalendarDialogTextConfig
import com.digitalcash.calendar.utils.CalendarDialogTextStyleConfig
import com.digitalcash.calendar.utils.CalenderUtils.getAvailableDays
import com.digitalcash.calendar.utils.CalenderUtils.getAvailableMonths
import com.digitalcash.calendar.utils.CalenderUtils.getAvailableYears
import com.digitalcash.calendar.utils.CalenderUtils.getDaysInMonth
import java.time.DateTimeException
import java.time.LocalDate
import java.time.Year
import java.time.chrono.ChronoLocalDate
import java.time.chrono.HijrahDate
import java.time.temporal.ChronoField

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CalendarDialog(
    maxDayOfMonth: Int,
    maxMonth: Int,
    maxYear: Int,
    selectedYear: Int,
    selectedMonth: Int,
    selectedDay: Int,
    yearsRange: IntRange,
    onDateSelected: (year: Int, month: Int, day: Int) -> Unit,
    onRangeSelected: (firstDate: LocalDate, secondDate: LocalDate) -> Unit,
    onMultiSelected: (List<LocalDate>) -> Unit,
    onDismissRequest: () -> Unit,
    calendarDateType: CalendarDateType = CalendarDateType.GREGORIAN,
    disableFutureDates: Boolean,
    enableRangeSelect: Boolean = false,
    enableMultiSelect: Boolean = false,
    textConfig: CalendarDialogTextConfig,
    colorConfig: CalendarDialogColorConfig,
    language: String,
    dimensionConfig: CalendarDialogDimensionConfig,
    textStyleConfig: CalendarDialogTextStyleConfig,
    confirmButtonContent: @Composable () -> Unit,
    cancelButtonContent: @Composable () -> Unit,
) {
    var currentYear by remember { mutableIntStateOf(selectedYear) }
    var currentMonth by remember { mutableIntStateOf(selectedMonth) }
    var currentDay by remember { mutableIntStateOf(selectedDay) }
    var isYearFlowVisible by remember { mutableStateOf(false) }
    var isMonthFlowVisible by remember { mutableStateOf(false) }

    var selectedDates by remember {
        mutableStateOf<List<ChronoLocalDate>>(
            listOf(
                if (calendarDateType == CalendarDateType.GREGORIAN) {
                    LocalDate.of(selectedYear, selectedMonth + 1, selectedDay)
                } else {
                    HijrahDate.of(selectedYear, selectedMonth + 1, selectedDay)
                },
            ),
        )
    }

    val availableDaysInMonth = getAvailableDays(
        selectedYear = maxYear,
        selectedMonth = maxMonth,
        currentDay = maxDayOfMonth,
        currentMonth = currentMonth,
        currentYear = currentYear,
        disableFutureDates = disableFutureDates,
        calendarDateType = calendarDateType,
    )
    val daysInMonth = getDaysInMonth(currentMonth, currentYear, calendarDateType)
    val availableMonths = getAvailableMonths(
        range = yearsRange,
        calendarDateType = calendarDateType,
        disableFutureDates = disableFutureDates,
        language = language,
        currentYear = currentYear,
        currentMonth = maxMonth,
    )
    val availableYears = getAvailableYears(yearsRange = yearsRange)

    AlertDialog(
        onDismissRequest = { onDismissRequest() },
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = currentYear.toString(),
                    modifier = Modifier
                        .clickable { isYearFlowVisible = !isYearFlowVisible }
                        .padding(dimensionConfig.buttonPadding),
                    style = textStyleConfig.yearHeaderStyle,
                )
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = textConfig.selectYearDescription,
                    modifier = Modifier
                        .clickable { isYearFlowVisible = !isYearFlowVisible }
                        .padding(end = dimensionConfig.buttonPadding),
                )
            }
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                if (isYearFlowVisible) {
                    FlowRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .verticalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalArrangement = Arrangement.spacedBy(
                            dimensionConfig.yearsFlowRowSpacing,
                            alignment = Alignment.CenterVertically,
                        ),
                        maxItemsInEachRow = 3,
                    ) {
                        availableYears.reversed().forEach { year ->
                            val boxColor =
                                if (currentYear == year) colorConfig.selectedYearColor else colorConfig.unSelectedYearColor

                            val yearTextStyle =
                                if (currentYear == year) textStyleConfig.selectedYearTextStyle else textStyleConfig.unselectedYearTextStyle

                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .background(
                                        color = boxColor,
                                        shape = RoundedCornerShape(dimensionConfig.dayBoxCornerRadius),
                                    ),
                            )
                            {
                                Text(
                                    text = year.toString(),
                                    modifier = Modifier
                                        .padding(dimensionConfig.buttonPadding)
                                        .clickable {
                                            currentYear = year
                                            val recalculateMonths = getAvailableMonths(
                                                range = yearsRange,
                                                calendarDateType = calendarDateType,
                                                disableFutureDates = disableFutureDates,
                                                language = "en",
                                                currentYear = currentYear,
                                                currentMonth = selectedMonth,
                                            )
                                            if (currentMonth + 1 >= recalculateMonths.size) {
                                                currentMonth = selectedMonth
                                            }
                                            getDaysInMonth(
                                                selectedMonth,
                                                selectedYear,
                                                calendarDateType,
                                            )

                                            if (currentDay !in availableDaysInMonth) {
                                                currentDay = selectedDay
                                            }
                                            isYearFlowVisible = false
                                        },
                                    style = yearTextStyle,
                                )
                            }
                        }
                    }
                } else if (isMonthFlowVisible) {
                    FlowRow(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(dimensionConfig.monthsFlowRowSpacing),
                        verticalArrangement = Arrangement.spacedBy(
                            dimensionConfig.monthsFlowRowSpacing,
                            alignment = Alignment.CenterVertically,
                        ),
                    ) {
                        availableMonths.forEachIndexed { index, month ->
                            val boxColor =
                                if (currentMonth == index) colorConfig.selectedMonthColor else colorConfig.unSelectedMonthColor
                            val monthTextStyle =
                                if (currentMonth == index) textStyleConfig.selectedMonthTextStyle else textStyleConfig.unselectedMonthTextStyle
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .background(
                                        color = boxColor,
                                        shape = RoundedCornerShape(dimensionConfig.dayBoxCornerRadius),
                                    ),
                            )
                            {
                                Text(
                                    text = month,
                                    modifier = Modifier
                                        .padding(dimensionConfig.buttonPadding)
                                        .clickable {
                                            currentMonth = index
                                            getDaysInMonth(
                                                selectedMonth,
                                                selectedYear,
                                                calendarDateType,
                                            )
                                            isMonthFlowVisible = false
                                        },
                                    style = monthTextStyle,
                                )
                            }

                        }
                    }
                } else {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        AnimatedVisibility(visible = (currentYear != yearsRange.first || currentMonth != 0)) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = textConfig.arrowBackDescription,
                                modifier = Modifier
                                    .clickable {
                                        if (currentMonth > 0) {
                                            currentMonth -= 1
                                        } else {
                                            if (currentYear > yearsRange.first) {
                                                currentYear -= 1
                                                currentMonth = 11
                                            }
                                        }
                                    }
                                    .padding(dimensionConfig.buttonPadding),
                            )
                        }

                        Spacer(modifier = Modifier.width(dimensionConfig.spacerWidth))

                        Text(
                            text = availableMonths[currentMonth],
                            modifier = Modifier
                                .padding(dimensionConfig.buttonPadding)
                                .clickable {
                                    isMonthFlowVisible =
                                        !isMonthFlowVisible
                                },
                        )

                        Spacer(modifier = Modifier.width(dimensionConfig.spacerWidth))

                        AnimatedVisibility(visible = (currentYear != yearsRange.last || currentMonth + 1 < availableMonths.size)) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = textConfig.arrowForwardDescription,
                                modifier = Modifier
                                    .clickable {
                                        if (currentMonth < 11) {
                                            if (currentMonth + 1 < availableMonths.size) {
                                                currentMonth += 1
                                            }
                                        } else {
                                            if (currentYear < yearsRange.last) {
                                                currentYear += 1
                                                currentMonth = 0
                                            }
                                        }
                                    }
                                    .padding(dimensionConfig.buttonPadding),
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(dimensionConfig.spacerHeight))

                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(dimensionConfig.daysFlowRowSpacing),
                        verticalArrangement = Arrangement.spacedBy(
                            dimensionConfig.daysFlowRowSpacing,
                            alignment = Alignment.CenterVertically,
                        ),
                    ) {
                        val isSelectedDayInvalid = currentDay !in availableDaysInMonth
                        if (isSelectedDayInvalid) {
                            currentDay = availableDaysInMonth.last()
                        }

                        if (enableRangeSelect) {
                            (1..daysInMonth).forEach { day ->

                                val isAvailable = day in availableDaysInMonth
                                val isSelected = if (isAvailable) {
                                    val validDate = when (calendarDateType) {
                                        CalendarDateType.GREGORIAN -> {
                                            try {
                                                !(currentMonth == 1 && day == 29 && !Year.of(
                                                    currentYear,
                                                ).isLeap)
                                            } catch (e: DateTimeException) {
                                                false
                                            }
                                        }

                                        CalendarDateType.HIJRI -> {
                                            try {
                                                HijrahDate.of(
                                                    currentYear,
                                                    currentMonth + 1,
                                                    day,
                                                )
                                                true
                                            } catch (e: DateTimeException) {
                                                false
                                            }
                                        }
                                    }

                                    if (validDate) {
                                        when (calendarDateType) {
                                            CalendarDateType.GREGORIAN -> selectedDates.contains(
                                                LocalDate.of(currentYear, currentMonth + 1, day),
                                            )

                                            CalendarDateType.HIJRI -> selectedDates.contains(
                                                HijrahDate.of(currentYear, currentMonth + 1, day),
                                            )
                                        }
                                    } else {
                                        false
                                    }
                                } else {
                                    false
                                }

                                val boxColor = when {
                                    isSelected -> colorConfig.selectedDayColor
                                    !isAvailable -> colorConfig.unAvailableDayColor
                                    else -> colorConfig.availableDayColor
                                }

                                val dayTextStyle = if (isSelected) {
                                    textStyleConfig.selectedDayTextStyle
                                } else if (isAvailable) {
                                    textStyleConfig.availableDayTextStyle
                                } else {
                                    textStyleConfig.unselectedDayTextStyle
                                }

                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier
                                        .size(dimensionConfig.dayBoxSize)
                                        .clickable(enabled = isAvailable) {
                                            if (isAvailable) {
                                                val validDate = when (calendarDateType) {
                                                    CalendarDateType.GREGORIAN -> {
                                                        try {
                                                            !(currentMonth == 1 && day == 29 && !Year.of(
                                                                currentYear,
                                                            ).isLeap)
                                                        } catch (e: DateTimeException) {
                                                            false
                                                        }
                                                    }

                                                    CalendarDateType.HIJRI -> {
                                                        try {
                                                            HijrahDate.of(
                                                                currentYear,
                                                                currentMonth + 1,
                                                                day,
                                                            )
                                                            true
                                                        } catch (e: DateTimeException) {
                                                            false
                                                        }
                                                    }
                                                }

                                                if (validDate) {
                                                    val selectedDate = when (calendarDateType) {
                                                        CalendarDateType.GREGORIAN -> LocalDate.of(
                                                            currentYear,
                                                            currentMonth + 1,
                                                            day,
                                                        )

                                                        CalendarDateType.HIJRI -> HijrahDate.of(
                                                            currentYear,
                                                            currentMonth + 1,
                                                            day,
                                                        )
                                                    }


                                                    selectedDates =
                                                        if (selectedDates.contains(selectedDate)) {
                                                            selectedDates.filter { it != selectedDate }
                                                        } else {
                                                            if (selectedDates.size < 2) {
                                                                selectedDates + selectedDate
                                                            } else {
                                                                listOf(
                                                                    selectedDates[1],
                                                                    selectedDate,
                                                                )
                                                            }
                                                        }
                                                }
                                            }
                                        }
                                        .background(
                                            color = boxColor,
                                            shape = RoundedCornerShape(dimensionConfig.dayBoxCornerRadius),
                                        ),
                                ) {
                                    Text(
                                        text = day.toString(),
                                        style = dayTextStyle,
                                    )
                                }
                            }
                        } else if (enableMultiSelect) {
                            (1..daysInMonth).forEach { day ->

                                val isAvailable = day in availableDaysInMonth
                                val isSelected = if (isAvailable) {
                                    val validDate = when (calendarDateType) {
                                        CalendarDateType.GREGORIAN -> {
                                            try {
                                                !(currentMonth == 1 && day == 29 && !Year.of(
                                                    currentYear,
                                                ).isLeap)
                                            } catch (e: DateTimeException) {
                                                false
                                            }
                                        }

                                        CalendarDateType.HIJRI -> {
                                            try {
                                                HijrahDate.of(
                                                    currentYear,
                                                    currentMonth + 1,
                                                    day,
                                                )
                                                true
                                            } catch (e: DateTimeException) {
                                                false
                                            }
                                        }
                                    }

                                    if (validDate) {
                                        when (calendarDateType) {
                                            CalendarDateType.GREGORIAN -> selectedDates.contains(
                                                LocalDate.of(currentYear, currentMonth + 1, day),
                                            )

                                            CalendarDateType.HIJRI -> selectedDates.contains(
                                                HijrahDate.of(currentYear, currentMonth + 1, day),
                                            )
                                        }
                                    } else {
                                        false
                                    }
                                } else {
                                    false
                                }

                                val boxColor = when {
                                    isSelected -> colorConfig.selectedDayColor
                                    !isAvailable -> colorConfig.unAvailableDayColor
                                    else -> colorConfig.availableDayColor
                                }

                                val dayTextStyle = if (isSelected) {
                                    textStyleConfig.selectedDayTextStyle
                                } else if (isAvailable) {
                                    textStyleConfig.availableDayTextStyle
                                } else {
                                    textStyleConfig.unselectedDayTextStyle
                                }

                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier
                                        .size(dimensionConfig.dayBoxSize)
                                        .clickable(enabled = isAvailable) {
                                            if (isAvailable) {
                                                val validDate = when (calendarDateType) {
                                                    CalendarDateType.GREGORIAN -> {
                                                        try {
                                                            !(currentMonth == 1 && day == 29 && !Year.of(
                                                                currentYear,
                                                            ).isLeap)
                                                        } catch (e: DateTimeException) {
                                                            false
                                                        }
                                                    }

                                                    CalendarDateType.HIJRI -> {
                                                        try {
                                                            HijrahDate.of(
                                                                currentYear,
                                                                currentMonth + 1,
                                                                day,
                                                            )
                                                            true
                                                        } catch (e: DateTimeException) {
                                                            false
                                                        }
                                                    }
                                                }

                                                if (validDate) {
                                                    val selectedDate = when (calendarDateType) {
                                                        CalendarDateType.GREGORIAN -> LocalDate.of(
                                                            currentYear,
                                                            currentMonth + 1,
                                                            day,
                                                        )

                                                        CalendarDateType.HIJRI -> HijrahDate.of(
                                                            currentYear,
                                                            currentMonth + 1,
                                                            day,
                                                        )
                                                    }


                                                    selectedDates =
                                                        if (selectedDates.contains(selectedDate)) {
                                                            selectedDates.filter { it != selectedDate }
                                                        } else {
                                                            selectedDates + selectedDate
                                                        }

                                                }
                                            }
                                        }
                                        .background(
                                            color = boxColor,
                                            shape = RoundedCornerShape(dimensionConfig.dayBoxCornerRadius),
                                        ),
                                ) {
                                    Text(
                                        text = day.toString(),
                                        style = dayTextStyle,
                                    )
                                }
                            }
                        } else {
                            (1..daysInMonth).forEach { day ->
                                val isAvailable = day in availableDaysInMonth
                                val isSelected = day == currentDay

                                val boxColor = when {
                                    isSelected -> colorConfig.selectedDayColor
                                    !isAvailable -> colorConfig.unAvailableDayColor
                                    else -> colorConfig.availableDayColor
                                }
                                val dayTextStyle =
                                    if (isSelected) textStyleConfig.selectedDayTextStyle else textStyleConfig.availableDayTextStyle
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier
                                        .size(dimensionConfig.dayBoxSize)
                                        .clickable(enabled = isAvailable) {
                                            currentDay = day
                                        }
                                        .background(
                                            color = boxColor,
                                            shape = RoundedCornerShape(dimensionConfig.dayBoxCornerRadius),
                                        ),
                                ) {
                                    Text(
                                        text = day.toString(),
                                        style = dayTextStyle,
                                    )
                                }
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (enableRangeSelect) {
                        if (selectedDates.size == 2) {
                            val firstDate = selectedDates[0]
                            val secondDate = selectedDates[1]

                            onRangeSelected(
                                LocalDate.of(
                                    firstDate.get(ChronoField.YEAR),
                                    firstDate.get(ChronoField.MONTH_OF_YEAR) - 1,
                                    firstDate.get(ChronoField.DAY_OF_MONTH),
                                ),
                                LocalDate.of(
                                    secondDate.get(ChronoField.YEAR),
                                    secondDate.get(ChronoField.MONTH_OF_YEAR) - 1,
                                    secondDate.get(ChronoField.DAY_OF_MONTH),
                                ),
                            )
                        }
                    } else if (enableMultiSelect) {
                        val datesList: MutableList<LocalDate> = mutableListOf()
                        selectedDates.forEach { date ->
                            datesList.add(
                                LocalDate.of(
                                    date.get(ChronoField.YEAR),
                                    date.get(ChronoField.MONTH_OF_YEAR) - 1,
                                    date.get(ChronoField.DAY_OF_MONTH),
                                ),
                            )
                            println(datesList)
                        }
                        onMultiSelected(datesList)
                    } else {
                        onDateSelected(currentYear, currentMonth, currentDay)
                    }
                },
            ) {
                confirmButtonContent()
            }
        },
        dismissButton = {
            TextButton(
                onClick = { onDismissRequest() },
            ) {
                cancelButtonContent()
            }
        },
        containerColor = colorConfig.dialogBackgroundColor,
    )
}


