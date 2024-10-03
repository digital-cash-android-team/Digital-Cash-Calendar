package com.digitalcash.digital_cash_calendar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import com.digitalcash.base.core.presentation.theme.AppTheme
import com.digitalcash.calendar.utils.CalendarDateType
import com.digitalcash.calendar.utils.CalendarDialogDimensionConfig
import com.digitalcash.calendar.utils.CalendarDialogTextConfig
import com.digitalcash.calendar.utils.CalendarState
import com.digitalcash.calendar.utils.CalendarViewType
import com.digitalcash.split.ui.main.calendar.ui.DgCalendar
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AppTheme(darkTheme = false) {
                var showDialog by remember {
                    mutableStateOf(true)
                }

                if (showDialog) {
                    DgCalendar(
                        calendarState = CalendarState(
                            selectedDate = LocalDate.now(),
                            calendarDateType = CalendarDateType.GREGORIAN,
                            calendarViewType = CalendarViewType.Dialog,
                            disableFutureDates = true,
                        ),
                        textConfig = CalendarDialogTextConfig(
                            selectButtonText = "Confirm",
                            cancelButtonText = "Close",
                            arrowBackDescription = "Back",
                            arrowForwardDescription = "Forward",
                        ),

                        dimensionConfig = CalendarDialogDimensionConfig(
                            dialogPadding = 20.dp,
                            buttonPadding = 10.dp,
                            iconSize = 30.dp,
                            dayBoxSize = 50.dp,
                            dayBoxCornerRadius = 25.dp,
                            spacerHeight = 20.dp,
                            spacerWidth = 10.dp,
                            daysFlowRowSpacing = 5.dp,
                        ),
                        onDateSelected = {
                            println(it.format(DateTimeFormatter.ISO_DATE))
                        },
                        onRangeSelected = { firstDate, secondDate ->
                            val (startDate, endDate) = if (secondDate.isBefore(firstDate)) {
                                secondDate to firstDate
                            } else {
                                firstDate to secondDate
                            }
                            println(
                                "From ${startDate.format(DateTimeFormatter.ISO_DATE)} To ${
                                    endDate.format(
                                        DateTimeFormatter.ISO_DATE,
                                    )
                                }",
                            )
                        },
                        onMultiSelected = {
                            println("dates list ${it.toList()}")
                        },
                        onDismissRequest = {
                            println("canceled")
                            showDialog = false
                        },
                    )

                }
            }

        }
    }
}




