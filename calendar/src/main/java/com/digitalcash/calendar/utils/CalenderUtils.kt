package com.digitalcash.calendar.utils

import android.icu.util.IslamicCalendar
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.digitalcash.calendar.R
import com.github.msarhan.ummalqura.calendar.UmmalquraCalendar
import java.time.YearMonth
import java.util.Calendar
import java.util.Locale

object CalenderUtils {
    fun getAvailableMonths(
        range: IntRange,
        calendarDateType: CalendarDateType,
        disableFutureDates: Boolean,
        currentYear: Int,
        language: String,
        currentMonth: Int,
    ): List<String> {
        val totalMonths = when (calendarDateType) {
            CalendarDateType.HIJRI -> {
                val hijriMonths = listOf(
                    "Muharram", "Safar", "Rabi al-Awwal", "Rabi al-Thani",
                    "Jumada al-Awwal", "Jumada al-Thani", "Rajab", "Sha'ban",
                    "Ramadan", "Shawwal", "Dhu al-Qi'dah", "Dhu al-Hijjah",
                )
                val hijriMonthsArabic = listOf(
                    "محرم", "سفر", "ربيع الأول", "ربيع الثاني",
                    "جمادى الأولى", "جمادى الثانية", "رجب", "شعبان",
                    "رمضان", "شوال", "ذو القعدة", "ذو الحجة",
                )
                if (language == "en") hijriMonths
                else hijriMonthsArabic
            }

            CalendarDateType.GREGORIAN -> {
                val gregorianMonths = listOf(
                    "January", "February", "March", "April",
                    "May", "June", "July", "August",
                    "September", "October", "November", "December",
                )
                val gregorianMonthsArabic = listOf(
                    "يناير", "فبراير", "مارس", "أبريل",
                    "مايو", "يونيو", "يوليو", "أغسطس",
                    "سبتمبر", "أكتوبر", "نوفمبر", "ديسمبر",
                )
                if (language == "en") gregorianMonths
                else gregorianMonthsArabic
            }
        }


        return if (disableFutureDates &&
            range.last == currentYear
        ) {
            totalMonths.take(currentMonth + 1)
        } else {
            totalMonths
        }
    }

    /**
     * retrieves the available days based on the selected year, month, and calendar type.
     */
    fun getAvailableDays(
        selectedYear: Int,
        selectedMonth: Int,
        currentDay: Int,
        currentMonth: Int,
        currentYear: Int,
        disableFutureDates: Boolean,
        calendarDateType: CalendarDateType,
    ): List<Int> {
        val daysInMonth = getDaysInMonth(currentMonth, currentYear, calendarDateType)
        return if (selectedYear == currentYear &&
            selectedMonth == currentMonth &&
            disableFutureDates
        ) {
            (1..currentDay).toList()
        } else {
            (1..daysInMonth).toList()
        }
    }

    fun getValidDayInMonth(month: Int, year: Int, selectedDay: Int): Int {
        val yearMonth = YearMonth.of(year, month) // Handles leap years and month boundaries
        val daysInMonth = yearMonth.lengthOfMonth()

        // Return the minimum of the selected day and the number of days in the month
        return selectedDay.coerceAtMost(daysInMonth)
    }

    /**
     * retrieves the available days based on the selected year, month, and calendar type.
     */
    fun getAvailableYears(
        yearsRange: IntRange,
    ): IntRange {
        return yearsRange
    }

    fun currentHijriYear(): Int {
        return IslamicCalendar().get(IslamicCalendar.YEAR)
    }

    /**
     * retrieves the number of days in a given month for the specified calendar type.
     */
    fun getDaysInMonth(month: Int, year: Int, calendarType: CalendarDateType): Int {
        return if (calendarType == CalendarDateType.HIJRI) {
            UmmalquraCalendar(Locale("ar")).apply {
                set(UmmalquraCalendar.YEAR, year)
                set(UmmalquraCalendar.MONTH, month)
            }.getActualMaximum(UmmalquraCalendar.DAY_OF_MONTH)
        } else {
            Calendar.getInstance().apply {
                set(Calendar.YEAR, year)
                set(Calendar.MONTH, month)
            }.getActualMaximum(Calendar.DAY_OF_MONTH)
        }
    }

    /**
     * retrieves the month name based on calendar type and language.
     */
    fun getMonthName(month: Int, calendarType: CalendarDateType, language: String): String {
        return when (calendarType) {
            CalendarDateType.HIJRI -> {
                val hijriMonths = listOf(
                    "Muharram", "Safar", "Rabi al-Awwal", "Rabi al-Thani",
                    "Jumada al-Awwal", "Jumada al-Thani", "Rajab", "Sha'ban",
                    "Ramadan", "Shawwal", "Dhu al-Qi'dah", "Dhu al-Hijjah",
                )
                val hijriMonthsArabic = listOf(
                    "محرم", "سفر", "ربيع الأول", "ربيع الثاني",
                    "جمادى الأولى", "جمادى الثانية", "رجب", "شعبان",
                    "رمضان", "شوال", "ذو القعدة", "ذو الحجة",
                )
                if (language == "en") hijriMonths[month]
                else hijriMonthsArabic[month]
            }

            CalendarDateType.GREGORIAN -> {
                val gregorianMonths = listOf(
                    "January", "February", "March", "April",
                    "May", "June", "July", "August",
                    "September", "October", "November", "December",
                )
                val gregorianMonthsArabic = listOf(
                    "يناير", "فبراير", "مارس", "أبريل",
                    "مايو", "يونيو", "يوليو", "أغسطس",
                    "سبتمبر", "أكتوبر", "نوفمبر", "ديسمبر",
                )
                if (language == "en") gregorianMonths[month]
                else gregorianMonthsArabic[month]
            }
        }
    }

    val Number.Sp
        @Composable
        get() = (this.toInt() / LocalDensity.current.fontScale).sp

    val poppinsRegular = androidx.compose.ui.text.font.FontFamily(
        Font(
            R.font.poppins_display_regular,
            FontWeight.Normal
        )
    )
    val poppinsSemiBold = androidx.compose.ui.text.font.FontFamily(
        Font(
            R.font.poppins_display_semibold,
            FontWeight.SemiBold
        )
    )
    val poppinsBold =
        androidx.compose.ui.text.font.FontFamily(Font(R.font.poppins_display_bold, FontWeight.Bold))
}
