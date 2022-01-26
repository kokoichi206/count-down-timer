package jp.mydns.kokoichi0206.countdowntimer.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

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

@Composable
fun ChangeActionBarColor(color: Color) {
    // Change ActionBar' color using systemuicontroller.
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(
        color = color,
    )
}
