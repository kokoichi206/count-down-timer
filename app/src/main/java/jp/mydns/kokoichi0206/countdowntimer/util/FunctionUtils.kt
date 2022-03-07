package jp.mydns.kokoichi0206.countdowntimer.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import java.time.LocalDateTime

/**
 * ２つの日時の時間差をミリ秒単位で取得する。
 *
 * @param[endDateTime] 終了予定時刻。
 * @param[startDateTime] 開始時刻。
 * @return 与えられた２つの日時の時間差。
 */
fun milliSecondsBetween2DateTime(endDateTime: LocalDateTime, startDateTime: LocalDateTime): Long {
    return milliSecondsFromLocalDataTime(endDateTime) - milliSecondsFromLocalDataTime(startDateTime)
}

/**
 * 日時からUNIX時間へと、ミリ秒単位で変換する。
 *
 * @param[dateTime] 時刻。
 * @return ミリ秒単位でのUNIX時間。
 */
fun milliSecondsFromLocalDataTime(dateTime: LocalDateTime): Long {
    return dateTime.atOffset(Constants.TokyoZoneOffset).toInstant().toEpochMilli()
}

/**
 * タイマーの中心に表示する文字列。
 * 『日付：時間：分:秒：ミリ秒』の形式（例: 2:23:21:43:337）
 *
 * @param[milliSeconds] ミリ秒単位での残り時間。
 * @return 整形された表示するための時間。
 */
fun formattedTimeFromMilliSeconds(milliSeconds: Int): String {
    if (milliSeconds < 0) {
        return "00:00:00:000"
    }
    val SEC = 1000
    val MIN = 60 * SEC
    val HOUR = 60 * MIN
    val DAY = 24 * HOUR
    val day = milliSeconds / DAY
    val hour = (milliSeconds - day * DAY) / HOUR
    val min = (milliSeconds - day * DAY - hour * HOUR) / MIN
    val sec = (milliSeconds - day * DAY - hour * HOUR - min * MIN) / SEC
    val milli = milliSeconds - day * DAY - hour * HOUR - min * MIN - sec * SEC
    return if (day > 0) {
        "%d:%02d:%02d:%02d:%03d".format(day, hour, min, sec, milli)
    } else {
        "%02d:%02d:%02d:%03d".format(hour, min, sec, milli)
    }
}

/**
 * ポモドーロタイマーの中心に表示する文字列。
 * 『分:秒』の形式（例: 21:43）
 *
 * @param[milliSeconds] ミリ秒単位での残り時間。
 * @return 整形された表示するための時間。
 */
fun minAndSecDisplayTime(milliSeconds: Long): String {
    return "%2d:%02d".format((milliSeconds / (60 * 1000)).toInt(), ((milliSeconds / 1000) % 60).toInt())
}

/**
 * アクションバーを指定の色に変えるためのComposable関数。
 *
 * @param[color] アクションバーの色。
 */
@Composable
fun ChangeActionBarColor(color: Color) {
    // Change ActionBar' color using systemuicontroller.
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(
        color = color,
    )
}
