package jp.mydns.kokoichi0206.countdowntimer.module.main.view

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import jp.mydns.kokoichi0206.countdowntimer.module.main.contract.MainContract
import jp.mydns.kokoichi0206.countdowntimer.util.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.time.LocalDateTime

/**
 * メイン画面を表示するComposable関数。
 *
 * ## 以下のComposable関数を使用
 * * [jp.mydns.kokoichi0206.countdowntimer.module.main.view.CircularProgressBar]
 * * [jp.mydns.kokoichi0206.countdowntimer.module.main.view.TitleTextField]
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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(paddingLarge),
    ) {
        // 縦画面ならそのまま表示する
        if (isVertical) {
            HomeContent(
                presenter = presenter,
                initialTitle = initialTitle,
                startedTime = startedTime,
                deadline = deadline,
                isVertical = isVertical,
            )
        } else {
            // 横画面なら横並びに表示させる
            Row() {
                HomeContent(
                    presenter = presenter,
                    initialTitle = initialTitle,
                    startedTime = startedTime,
                    deadline = deadline,
                    isVertical = isVertical,
                )
            }
        }
    }
    // Change ActionBar color
    ChangeActionBarColor(color = MaterialTheme.colors.background)
}

@Composable
fun HomeContent(
    presenter: MainContract.Presenter,
    initialTitle: String? = null,
    startedTime: LocalDateTime? = null,
    deadline: LocalDateTime? = null,
    isVertical: Boolean = true,
) {
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

    val focusManager = LocalFocusManager.current

    val titleStyle = TextStyle(
        color = Color.White.copy(alpha = 0.7f),
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
    )

    // タイトルとメニューを横に並べている
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // タイトル入力欄
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

                runBlocking {
                    presenter.onTitleRegistered(title.value)
                }
            }
        )

        // More の三点ボタンのドロップダウンメニュー
        var expanded by remember { mutableStateOf(false) }
        Box(
            modifier = Modifier
                .wrapContentSize(Alignment.TopStart)
        ) {
            IconButton(
                modifier = Modifier
                    .padding(vertical = 0.dp)
                    .testTag(TestTags.MORE_VERT_ICON),
                onClick = {
                    expanded = true
                }
            ) {
                Icon(
                    Icons.Filled.MoreVert,
                    contentDescription = Constants.DESCRIPTION_MORE_VERT_ICON,
                    tint = MaterialTheme.colors.surface,
                )
            }
            DropdownMenu(
                modifier = Modifier
                    .background(MaterialTheme.colors.background)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colors.onSurface),
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                // License menu
                DropdownMenuItem(
                    modifier = Modifier
                        .testTag(TestTags.LICENSE_MENU),
                    onClick = {
                        expanded = false
                        // presenter に通知
                        presenter.onLicenseClicked()
                    }
                ) {
                    Text(
                        text = Constants.LICENSE_MENU,
                        fontSize = 24.sp,
                        color = Color.White,
                    )
                }

                // Privacy Policy menu
                DropdownMenuItem(
                    modifier = Modifier
                        .testTag(TestTags.PRIVACY_POLICY_MENU),
                    onClick = {
                        expanded = false
                        // presenter に通知
                        presenter.onPrivacyPolicyClicked()
                    }
                ) {
                    Text(
                        text = Constants.PRIVACY_POLICY_MENU,
                        fontSize = 24.sp,
                        color = Color.White,
                    )
                }
            }
        }
    }

    val strokeWidth = 12.dp
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .clip(shape = RectangleShape)
            .padding(strokeWidth / 2),
        contentAlignment = Alignment.Center,
    ) {
        val boxWithConstraintsScope = this

        // タイマーの円
        CircularProgressBar(
            percentage = if (cTime > Constants.AdditionalTime) {
                percentage
            } else {
                1f
            },
            displayTime = formattedTimeFromMilliSeconds(cTime.toInt()),
            fontSize = 36.sp,
            radius = boxWithConstraintsScope.maxWidth / 2,
            color = circleColor,
            strokeWidth = strokeWidth,
            onNumberClick = {
                // FIXME: 更新を通知するために無理矢理 NONE を挟んでいる
                step = SelectionStep.NONE
                step = SelectionStep.DATE
            },
            needCircleShow = needCircleShow,
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
