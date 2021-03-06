package jp.mydns.kokoichi0206.countdowntimer.module.main.view

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import jp.mydns.kokoichi0206.countdowntimer.module.main.contract.MainContract
import jp.mydns.kokoichi0206.countdowntimer.module.main.view.components.HomeViewContent
import jp.mydns.kokoichi0206.countdowntimer.util.ChangeActionBarColor
import jp.mydns.kokoichi0206.countdowntimer.util.Constants
import jp.mydns.kokoichi0206.countdowntimer.util.formattedTimeFromMilliSeconds
import jp.mydns.kokoichi0206.countdowntimer.util.milliSecondsBetween2DateTime
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.time.LocalDateTime

/**
 * メイン画面を表示するComposable関数。
 *
 * ## 以下のComposable関数を使用
 * * [jp.mydns.kokoichi0206.countdowntimer.module.main.view.components.CircularProgressBar]
 * * [jp.mydns.kokoichi0206.countdowntimer.module.main.view.components.TitleTextField]
 */
@ExperimentalComposeUiApi
@Composable
fun Home(
    presenter: MainContract.Presenter,
    initialTitle: String? = null,
    startedTime: LocalDateTime? = null,
    deadline: LocalDateTime? = null,
) {

    val paddingLarge = 24.dp

    var isVertical by remember {
        mutableStateOf(true)
    }
    isVertical = LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT

    // Change ActionBar color
    ChangeActionBarColor(color = MaterialTheme.colors.background)

    val primeColor = MaterialTheme.colors.primary

    var isPlaying by remember { mutableStateOf(false) }

    var step by remember {
        mutableStateOf(
            SelectionStep.NONE
        )
    }

    val title = remember {
        mutableStateOf(initialTitle ?: "")
    }

    // For backup
    var startedAt by remember {
        mutableStateOf(startedTime ?: LocalDateTime.now())
    }

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

    // サークルを表示させるかどうか
    var needCircleShow by remember {
        mutableStateOf(true)
    }
    // Circle bar color
    var circleColor by remember {
        mutableStateOf(primeColor)
    }

    // initialize
    if (cTime != 0L && step == SelectionStep.NONE) {
        step = SelectionStep.DONE
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(paddingLarge),
    ) {
        // 縦画面ならそのまま表示する
        if (isVertical) {
            HomeViewContent(
                title = title.value,
                onTitleFocusChanged = {
                    // FIXME: これをつけないとダイアログが立ち上がってしまう。
                    step = SelectionStep.NONE
                },
                onTitleValueChanged = {
                    title.value = it
                },
                onTitleDone = {
                    runBlocking {
                        presenter.onTitleRegistered(title.value)
                    }
                },
                onHomeMenuClick = {
                    runBlocking {
                        presenter.onHomeMenuClicked()
                    }
                },
                onPomodoroMenuClick = {
                    presenter.onPomodoroMenuClicked()
                },
                onLicenseMenuClick = {
                    // presenter に通知
                    presenter.onLicenseClicked()
                },
                onPrivacyPolicyMenuClick = {
                    // presenter に通知
                    presenter.onPrivacyPolicyClicked()
                },
                circlePercentage = if (cTime > Constants.AdditionalTime) {
                    percentage
                } else {
                    1f
                },
                circleDisplayTime = formattedTimeFromMilliSeconds(cTime.toInt()),
                circleColor = circleColor,
                onCircleNumberClick = {
                    // FIXME: 更新を通知するために無理矢理 NONE を挟んでいる
                    step = SelectionStep.NONE
                    step = SelectionStep.DATE
                },
                needCircleShow = needCircleShow,
            )
        } else {
            // 横画面なら横並びに表示させる
            Row() {
                HomeViewContent(
                    title = title.value,
                    onTitleFocusChanged = {
                        // FIXME: これをつけないとダイアログが立ち上がってしまう。
                        step = SelectionStep.NONE
                    },
                    onTitleValueChanged = {
                        title.value = it
                    },
                    onTitleDone = {
                        runBlocking {
                            presenter.onTitleRegistered(title.value)
                        }
                    },
                    onHomeMenuClick = {
                        runBlocking {
                            presenter.onHomeMenuClicked()
                        }
                    },
                    onPomodoroMenuClick = {
                        presenter.onPomodoroMenuClicked()
                    },
                    onLicenseMenuClick = {
                        // presenter に通知
                        presenter.onLicenseClicked()
                    },
                    onPrivacyPolicyMenuClick = {
                        // presenter に通知
                        presenter.onPrivacyPolicyClicked()
                    },
                    circlePercentage = if (cTime > Constants.AdditionalTime) {
                        percentage
                    } else {
                        1f
                    },
                    circleDisplayTime = formattedTimeFromMilliSeconds(cTime.toInt()),
                    circleColor = circleColor,
                    onCircleNumberClick = {
                        // FIXME: 更新を通知するために無理矢理 NONE を挟んでいる
                        step = SelectionStep.NONE
                        step = SelectionStep.DATE
                    },
                    needCircleShow = needCircleShow,
                )
            }
        }
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
        onCloseRequest = {
            dateDialogState.hide()
            step = SelectionStep.NONE
        },
    ) {
        datepicker { date ->
            selectedDate = date
            // Next Step
            step = SelectionStep.TIME
        }
    }

    // Time picker dialog
    val timeDialogState = rememberMaterialDialogState()
    var selectedTime by remember {
        mutableStateOf(Constants.DefaultSelectedTime)
    }
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
        },
        onCloseRequest = {
            dateDialogState.hide()
            step = SelectionStep.NONE
        },
    ) {
        timepicker { time ->
            // DataTime setup finished!
            selectedTime = time
            step = SelectionStep.DONE

            // 必要な変数を初期化
            deadLine = LocalDateTime.of(selectedDate, selectedTime)
            startedAt = LocalDateTime.now()
            cTime = milliSecondsBetween2DateTime(deadLine, startedAt)
            needCircleShow = true

            runBlocking {
                presenter.onDateTimeRegistered(title.value, startedAt, deadLine)
            }
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

    // Timer
    LaunchedEffect(key1 = deadLine, key2 = cTime, key3 = isVertical) {
        if (cTime > Constants.AdditionalTime) {
            delay(Constants.TimerInterval)
            cTime = milliSecondsBetween2DateTime(deadLine, LocalDateTime.now())

            percentage = cTime.toFloat() / milliSecondsBetween2DateTime(deadLine, startedAt)

            circleColor = when {
                percentage < 1f / 6 -> {
                    Color.Red
                }
                percentage < 3f / 6 -> {
                    Color.Yellow
                }
                else -> {
                    primeColor
                }
            }
        }
        // 初めてタイマーが0以下になった時、presenter.onFinishTimer()を呼び出す。
        if (cTime > 0) {
            isPlaying = true
        } else if (cTime < 0) {
            if (isPlaying) {
                isPlaying = false
                presenter.onFinishTimer()
            }
        }
        // 終了時刻後は、サークルを点灯させる
        needCircleShow = if (Constants.AdditionalTime < cTime && cTime < 0) {
            ((cTime / 1000) % 2).toInt() == 0
        } else {
            true
        }
    }
}

/**
 * メイン画面のタイマーの選択状態を表すenumクラス。
 */
enum class SelectionStep(val idx: Int) {
    NONE(0),
    DATE(1),
    TIME(2),
    DONE(3),
}
