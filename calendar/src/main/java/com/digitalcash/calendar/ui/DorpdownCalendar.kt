package com.digitalcash.split.ui.main.calendar.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.digitalcash.calendar.utils.CalendarState
import com.digitalcash.calendar.utils.CalenderUtils.getAvailableDays
import com.digitalcash.calendar.utils.CalenderUtils.getAvailableMonths
import com.digitalcash.calendar.utils.CalenderUtils.getAvailableYears
import com.digitalcash.calendar.utils.CalenderUtils.getDaysInMonth
import com.digitalcash.calendar.utils.CalenderUtils.getMonthName

@Composable
fun DropDownCalendar(
    maxDayOfMonth: Int,
    maxMonth: Int,
    maxYear: Int,
    selectedYear: Int,
    selectedDay: Int,
    selectedMonth: Int,
    language: String = "ar",
    calendarState: CalendarState,
) {
    var selectedYear1 by remember {
        mutableIntStateOf(selectedYear)
    }

    var selectedMonth1 by remember {
        mutableIntStateOf(selectedMonth)
    }

    var selectedDay1 by remember {
        mutableIntStateOf(selectedDay)
    }


    val daysInMonth =
        if (selectedYear1 == maxYear && selectedMonth1 >= maxMonth) {
            getAvailableDays(
                selectedYear = selectedYear1,
                selectedMonth = selectedMonth1,
                currentDay = maxDayOfMonth,
                currentMonth = maxMonth,
                currentYear = maxYear,
                disableFutureDates = calendarState.disableFutureDates,
                calendarDateType = calendarState.calendarDateType,
            )
        } else {
            (1..getDaysInMonth(
                selectedMonth1,
                selectedYear1,
                calendarType = calendarState.calendarDateType,
            )).toList()
        }

    val availableMonths = getAvailableMonths(
        currentMonth = maxMonth,
        currentYear = selectedYear1,
        calendarDateType = calendarState.calendarDateType,
        disableFutureDates = calendarState.disableFutureDates,
        language = language,
        range = calendarState.range,
    )

    val availableYears = getAvailableYears(
        yearsRange = calendarState.range,
    )
    println("available years is $availableYears")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (selectedYear1 in availableYears) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                DropdownSelector(
                    options = daysInMonth,
                    selectedOption = selectedDay1,
                    onOptionSelected = { selectedDay1 = it },
                )

                DropdownSelector(
                    options = availableMonths.indices.toList(),
                    selectedOption = selectedMonth1,
                    onOptionSelected = {
                        selectedMonth1 = it
                        selectedDay1 =
                            if (selectedYear1 == maxYear && it >= maxMonth && selectedDay1 > maxDayOfMonth)
                                maxDayOfMonth
                            else
                                selectedDay1

                    },
                    optionLabel = { monthIndex ->
                        getMonthName(
                            month = monthIndex,
                            calendarType = calendarState.calendarDateType,
                            language = "ar",
                        )
                    },
                )

                DropdownSelector(
                    options = availableYears.toList().reversed(),
                    selectedOption = selectedYear1,
                    onOptionSelected = {
                        selectedYear1 = it
                        selectedMonth1 =
                            if (selectedYear1 == maxYear && selectedMonth1 > maxMonth) maxMonth else selectedMonth1
                        selectedDay1 =
                            if (selectedYear1 == maxYear && selectedMonth1 >= maxMonth && selectedDay1 > maxDayOfMonth)
                                maxDayOfMonth
                            else
                                selectedDay1
                    },
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Selected Date: $selectedDay1/${selectedMonth1 + 1}/$selectedYear1")
        } else {
            Text(text = "Selected year is Out Of Range")
        }
    }
}