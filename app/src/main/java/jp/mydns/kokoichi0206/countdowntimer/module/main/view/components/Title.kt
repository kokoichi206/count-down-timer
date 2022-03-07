package jp.mydns.kokoichi0206.countdowntimer.module.main.view.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun Title(
    modifier: Modifier = Modifier,
    title: String,
) {
    val titleStyle = TextStyle(
        color = Color.White.copy(alpha = 0.7f),
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
    )

    Box(
        modifier = modifier
    ) {
        Text(
            text = title,
            style = titleStyle,
        )
    }
}