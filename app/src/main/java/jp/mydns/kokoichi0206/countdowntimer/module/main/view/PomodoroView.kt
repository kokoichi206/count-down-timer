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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import jp.mydns.kokoichi0206.countdowntimer.module.main.contract.MainContract
import jp.mydns.kokoichi0206.countdowntimer.module.main.view.components.PomodoroViewContent
import jp.mydns.kokoichi0206.countdowntimer.util.ChangeActionBarColor
import jp.mydns.kokoichi0206.countdowntimer.util.Constants
import jp.mydns.kokoichi0206.countdowntimer.util.milliSecondsBetween2DateTime
import jp.mydns.kokoichi0206.countdowntimer.util.minAndSecDisplayTime
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.time.LocalDateTime

/**
 * ポモドーロタイマーの画面を表示するComposable関数。
 */
@ExperimentalComposeUiApi
@Composable
fun PomodoroView(
    presenter: MainContract.Presenter,
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


    var deadLine by remember {
        mutableStateOf(deadline ?: Constants.DefaultDataTime)
    }

    var isWorking by remember { mutableStateOf(true) }

    var timeLeft by remember { mutableStateOf(Constants.POMODORO_WORK_TIME_MILLI_SECONDS) }

    var percentage by remember {
        mutableStateOf(
            if (!isWorking) {
                1f
            } else {
                timeLeft.toFloat() / if (isWorking) {
                    Constants.POMODORO_WORK_TIME_MILLI_SECONDS
                } else {
                    Constants.POMODORO_REST_TIME_MILLI_SECONDS
                }
            }
        )
    }

    // Circle bar color
    var circleColor by remember {
        mutableStateOf(primeColor)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(paddingLarge),
    ) {
        // 縦画面ならそのまま表示する
        if (isVertical) {
            PomodoroViewContent(
                title = if (isWorking) {
                    Constants.POMODORO_TITLE_WORKING
                } else {
                    Constants.POMODORO_TITLE_RESTING
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
                circlePercentage = if (isPlaying) {
                    percentage
                } else {
                    1f
                },
                circleDisplayTime = minAndSecDisplayTime(timeLeft),
                circleColor = circleColor,
                onCircleNumberClick = {
                    isPlaying = !isPlaying

                    deadLine = LocalDateTime.now().plusSeconds(timeLeft / 1000)
                },
            )
        } else {
            // 横画面なら横並びに表示させる
            Row() {
                PomodoroViewContent(
                    titleModifier = Modifier.weight(1f),
                    circleModifier = Modifier.weight(1f),
                    title = if (isWorking) {
                        Constants.POMODORO_TITLE_WORKING
                    } else {
                        Constants.POMODORO_TITLE_RESTING
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
                    circlePercentage = if (isPlaying) {
                        percentage
                    } else {
                        1f
                    },
                    circleDisplayTime = minAndSecDisplayTime(timeLeft),
                    circleColor = circleColor,
                    onCircleNumberClick = {
                        isPlaying = !isPlaying

                        deadLine = LocalDateTime.now().plusSeconds(timeLeft / 1000)
                    },
                )
            }
        }
    }


    // Timer
    LaunchedEffect(key1 = percentage, key2 = isVertical, key3 = isPlaying) {
        if (isPlaying) {
            delay(Constants.TimerInterval)

            timeLeft = milliSecondsBetween2DateTime(deadLine, LocalDateTime.now())

            percentage = timeLeft.toFloat() / if (isWorking) {
                Constants.POMODORO_WORK_TIME_MILLI_SECONDS
            } else {
                Constants.POMODORO_REST_TIME_MILLI_SECONDS
            }

            // 初めてタイマーが0以下になった時。
            if (timeLeft < 0) {
                isWorking = !isWorking
                timeLeft = if (isWorking) {
                    Constants.POMODORO_WORK_TIME_MILLI_SECONDS
                } else {
                    Constants.POMODORO_REST_TIME_MILLI_SECONDS
                }
                deadLine = LocalDateTime.now().plusSeconds(timeLeft / 1000)

                // presenterに通知。
                presenter.onFinishTimer()
            }
        }
    }
}
