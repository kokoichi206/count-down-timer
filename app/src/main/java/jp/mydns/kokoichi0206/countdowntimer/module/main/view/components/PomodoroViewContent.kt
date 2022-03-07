package jp.mydns.kokoichi0206.countdowntimer.module.main.view.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PomodoroViewContent(
    titleModifier: Modifier = Modifier,
    circleModifier: Modifier = Modifier,
    title: String,
    onHomeMenuClick: () -> Unit = {},
    onPomodoroMenuClick: () -> Unit = {},
    onLicenseMenuClick: () -> Unit = {},
    onPrivacyPolicyMenuClick: () -> Unit = {},
    circlePercentage: Float,
    circleDisplayTime: String = "00:00",
    circleColor: Color = MaterialTheme.colors.primary,
    onCircleNumberClick: () -> Unit = {},
    needCircleShow: Boolean = true,
) {
    // タイトルとメニューを横に並べている
    Row(
        modifier = titleModifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Title(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp),
            title = title,
        )

        MyDropDownMenu(
            onHomeMenuClick = {
                onHomeMenuClick()
            },
            onPomodoroMenuClick = {
                onPomodoroMenuClick()
            },
            onLicenseMenuClick = {
                onLicenseMenuClick()
            },
            onPrivacyPolicyMenuClick = {
                onPrivacyPolicyMenuClick()
            }
        )
    }

    val strokeWidth = 12.dp
    BoxWithConstraints(
        modifier = circleModifier
            .fillMaxSize()
            .clip(shape = RectangleShape)
            .padding(strokeWidth / 2),
        contentAlignment = Alignment.Center,
    ) {
        val boxWithConstraintsScope = this

        // タイマーの円
        CircularProgressBar(
            percentage = circlePercentage,
            displayTime = circleDisplayTime,
            fontSize = 36.sp,
            radius = boxWithConstraintsScope.maxWidth / 2,
            color = circleColor,
            strokeWidth = strokeWidth,
            onNumberClick = {
                onCircleNumberClick()
            },
            needCircleShow = needCircleShow,
        )
    }
}
