package jp.mydns.kokoichi0206.countdowntimer.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
object Constants {

    const val TIMER_TITLE_PLACEHOLDER = "TITLE"

    // DateTime picker dialog
    const val OK_BUTTON_TEXT = "Ok"
    const val CANCEL_BUTTON_TEXT = "Cancel"

    val DefaultDataTime = LocalDateTime.parse(
        "2016-03-04 11:30:40",
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    )
    val DefaultSelectedDate = LocalDate.of(2011, 1, 1)
    val DefaultSelectedTime = LocalTime.of(0, 1, 2)

    val TokyoZoneOffset = ZoneOffset.ofHours(+9)

    val TimerInterval = 100L
}
