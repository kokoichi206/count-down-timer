package jp.mydns.kokoichi0206.countdowntimer.module.main.view.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jp.mydns.kokoichi0206.countdowntimer.util.TestTags

/**
 * タイマー時計を表すComposable関数。
 */
@Composable
fun CircularProgressBar(
    modifier: Modifier = Modifier,
    percentage: Float,
    displayTime: String = "00:00:00",
    fontSize: TextUnit = 28.sp,
    radius: Dp = 50.dp,
    color: Color = Color.Green,
    strokeWidth: Dp = 8.dp,
    animDuration: Int = 100,
    animDelay: Int = 0,
    onNumberClick: () -> Unit = {},
    needCircleShow: Boolean,
) {
    var animationPlayed by remember {
        mutableStateOf(false)
    }
    val curPercentage = animateFloatAsState(
        targetValue = if (animationPlayed) percentage else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = animDelay
        )
    )
    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.size(radius * 2f)
    ) {
        Canvas(
            modifier = Modifier
                .size(radius * 2f)
                .testTag(TestTags.HOME_CIRCLE)
        ) {
            if (needCircleShow) {
                drawArc(
                    color = color,
                    -90f,
                    if (curPercentage.value > 0) {
                        360 * curPercentage.value
                    } else {
                        360 * 1f
                    },
                    useCenter = false,
                    style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
                )
            }
        }
        Text(
            modifier = Modifier
                .clickable {
                    onNumberClick()
                }
                .testTag(TestTags.HOME_DISPLAYED_TIME),
            text = displayTime,
            color = Color.White.copy(alpha = 0.7f),
            fontSize = fontSize,
            fontWeight = FontWeight.Bold
        )
    }
}
