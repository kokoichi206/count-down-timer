package jp.mydns.kokoichi0206.countdowntimer.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

/**
 * 定数を集めたオブジェクト。
 */
@RequiresApi(Build.VERSION_CODES.O)
object Constants {

    const val TIMER_TITLE_PLACEHOLDER = "TITLE"

    // DateTime picker dialog
    const val OK_BUTTON_TEXT = "Ok"
    const val CANCEL_BUTTON_TEXT = "Cancel"

    const val DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss"
    val DefaultDataTime = LocalDateTime.parse(
        "2016-03-04 11:30:40",
        DateTimeFormatter.ofPattern(DATETIME_PATTERN)
    )
    val DefaultSelectedDate = LocalDate.of(2011, 1, 1)
    val DefaultSelectedTime = LocalTime.of(0, 1, 2)

    val TokyoZoneOffset = ZoneOffset.ofHours(+9)

    const val TimerInterval = 10L
    // 終了時刻後からループを継続する時間
    const val AdditionalTime = -60_000L

    // In MoreVert
    const val DESCRIPTION_MORE_VERT_ICON = "MoreVert icon"
    const val LICENSE_MENU = "Licenses"
    const val LICENSE_TITLE = "License Information"
    const val PRIVACY_POLICY_MENU = "Privacy Policy"
}
