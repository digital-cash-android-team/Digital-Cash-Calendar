package com.digitalcash.calendar.utils

import com.digitalcash.calendar.utils.CalenderUtils.currentHijriYear
import java.time.LocalDate


data class CalendarState(
    val selectedDate: LocalDate = LocalDate.now(),
    val calendarDateType: CalendarDateType = CalendarDateType.HIJRI,
    val calendarViewType: CalendarViewType = CalendarViewType.DropDown,
    val range: IntRange = if (calendarDateType == CalendarDateType.HIJRI) 1301..currentHijriYear() else 1990..LocalDate.now().year,
    val disableFutureDates: Boolean = false,
    val enableRangeSelect: Boolean = false,
    val enableMultiSelect: Boolean = false,
)
