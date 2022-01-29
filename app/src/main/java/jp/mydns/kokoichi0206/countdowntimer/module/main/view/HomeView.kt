package jp.mydns.kokoichi0206.countdowntimer.module.main.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import jp.mydns.kokoichi0206.countdowntimer.module.main.contract.MainContract
import jp.mydns.kokoichi0206.countdowntimer.util.ChangeActionBarColor
import jp.mydns.kokoichi0206.countdowntimer.util.Constants
import jp.mydns.kokoichi0206.countdowntimer.util.formattedTimeFromMilliSeconds
import jp.mydns.kokoichi0206.countdowntimer.util.milliSecondsBetween2DateTime
import kotlinx.coroutines.delay
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalComposeUiApi
@Composable
fun Home(
    presenter: MainContract.Presenter,
    startedTime: LocalDateTime? = null,
    deadline: LocalDateTime? = null,
) {
    val paddingLarge = 24.dp

    var step by remember {
        mutableStateOf(
            SelectionStep.NONE
        )
    }

    // For backup
    var startedAt = startedTime ?: LocalDateTime.now()

    var deadLine by remember {
        mutableStateOf(deadline ?: Constants.DefaultDataTime)
    }
    var cTime by remember {
        mutableStateOf(
            if (deadLine == null) {
                0L
            } else {
                milliSecondsBetween2DateTime(
                    deadLine,
                    LocalDateTime.now()
                )
            }
        )
    }
    var percentage by remember {
        mutableStateOf(
            if (deadline == null || startedTime == null) {
                1f
            } else {
                cTime.toFloat() / milliSecondsBetween2DateTime(deadLine, startedAt)
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(paddingLarge),
    ) {
        val title = remember {
            mutableStateOf("")
        }
        val focusManager = LocalFocusManager.current

        val titleStyle = TextStyle(
            color = MaterialTheme.colors.primary,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
        )
        TitleTextField(
            onFocusChanged = {
                // FIXME: これをつけないとダイアログが立ち上がってしまう。
                step = SelectionStep.NONE
            },
            title = title.value,
            placeHolder = Constants.TIMER_TITLE_PLACEHOLDER,
            onValueChanged = {
                title.value = it
            },
            textStyle = titleStyle,
            onDone = {
                focusManager.clearFocus()
            }
        )

        val strokeWidth = 12.dp
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .clip(shape = RectangleShape)
                .padding(strokeWidth / 2),
            contentAlignment = Alignment.Center,
        ) {
            val boxWithConstraintsScope = this

            CircularProgressBar(
                percentage = if (cTime > 0) {
                    percentage
                } else {
                    1f
                },
                displayTime = formattedTimeFromMilliSeconds(cTime.toInt()),
                fontSize = 36.sp,
                radius = boxWithConstraintsScope.maxWidth / 2,
                color = MaterialTheme.colors.primary,
                strokeWidth = strokeWidth,
                onNumberClick = {
                    // FIXME: 更新を通知するために無理矢理 NONE を挟んでいる
                    step = SelectionStep.NONE
                    step = SelectionStep.DATE
                }
            )
        }

        // Date picker dialog
        val dateDialogState = rememberMaterialDialogState()
        var selectedDate by remember {
            mutableStateOf(Constants.DefaultSelectedDate)
        }

        MaterialDialog(
            dialogState = dateDialogState,
            buttons = {
                positiveButton(Constants.OK_BUTTON_TEXT)
                negativeButton(
                    text = Constants.CANCEL_BUTTON_TEXT,
                    onClick = {
                        step = SelectionStep.NONE
                    }
                )
            },
        ) {
            datepicker { date ->
                selectedDate = date
                // Next Step
                step = SelectionStep.TIME
            }
        }

        // Time picker dialog
        var selectedTime by remember {
            mutableStateOf(Constants.DefaultSelectedTime)
        }
        val timeDialogState = rememberMaterialDialogState()
        MaterialDialog(
            dialogState = timeDialogState,
            buttons = {
                positiveButton(Constants.OK_BUTTON_TEXT)
                negativeButton(
                    text = Constants.CANCEL_BUTTON_TEXT,
                    onClick = {
                        step = SelectionStep.NONE
                    }
                )
            }
        ) {
            timepicker { time ->
                // DataTime setup finished!
                selectedTime = time
                step = SelectionStep.DONE

                deadLine = LocalDateTime.of(selectedDate, selectedTime)
                startedAt = LocalDateTime.now()

                cTime = milliSecondsBetween2DateTime(deadLine, startedAt)

                presenter.onDateTimeRegistered(startedAt, deadLine)
            }
        }
        when (step) {
            SelectionStep.DATE -> {
                dateDialogState.show()
            }
            SelectionStep.TIME -> {
                timeDialogState.show()
            }
            SelectionStep.NONE, SelectionStep.DONE -> {}
        }
    }

    // Timer
    LaunchedEffect(key1 = deadLine, key2 = cTime) {
        if (cTime > 0) {
            delay(Constants.TimerInterval)
            cTime = milliSecondsBetween2DateTime(deadLine, LocalDateTime.now())

            percentage = cTime.toFloat() / milliSecondsBetween2DateTime(deadLine, startedAt)
        }
    }

    // Change ActionBar color
    ChangeActionBarColor(color = MaterialTheme.colors.background)
}

enum class SelectionStep(val idx: Int) {
    NONE(0),
    DATE(1),
    TIME(2),
    DONE(3),
}
