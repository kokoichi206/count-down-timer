package jp.mydns.kokoichi0206.countdowntimer.module.main.view

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.ExperimentalComposeUiApi
import jp.mydns.kokoichi0206.countdowntimer.datamanager.SoundPoolManager
import jp.mydns.kokoichi0206.countdowntimer.module.main.assembler.MainAssembler
import jp.mydns.kokoichi0206.countdowntimer.module.main.contract.MainContract
import jp.mydns.kokoichi0206.countdowntimer.ui.theme.CountDownTimerTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.time.LocalDateTime

/**
 * メイン画面のViewクラス。
 */
open class MainActivity : ComponentActivity(), MainContract.View {
    /**
     * presenterクラス。
     */
    lateinit var presenter: MainContract.Presenter

    /**
     * 音源のmanagerクラス。
     */
    lateinit var soundPoolManager: SoundPoolManager

    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = beginAssembleModules(this)
        runBlocking {
            withContext(Dispatchers.IO) {
                presenter.onCreate()
            }
        }
        soundPoolManager = SoundPoolManager(this)
    }

    /**
     * メイン画面のcompose viewをセットする。
     *
     * テーマを設定した上で、以下のComposable関数を表示。
     *
     * [jp.mydns.kokoichi0206.countdowntimer.module.main.view.Home]
     */
    @ExperimentalComposeUiApi
    override fun setMainView() {
        setContent {
            CountDownTimerTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Home(
                        presenter = presenter,
                    )
                }
            }
        }
    }

    /**
     * メイン画面のcompose viewを初期値付きでセットする。
     *
     * テーマを設定した上で、以下のComposable関数を表示。
     *
     * [jp.mydns.kokoichi0206.countdowntimer.module.main.view.Home]
     */
    @ExperimentalComposeUiApi
    override fun setMainViewWithTime(
        title: String,
        startedAt: LocalDateTime,
        deadLine: LocalDateTime
    ) {
        setContent {
            CountDownTimerTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Home(
                        presenter = presenter,
                        initialTitle = title,
                        startedTime = startedAt,
                        deadline = deadLine,
                    )
                }
            }
        }
    }

    /**
     * ポモドーロタイマー画面のcompose viewをセットする。
     *
     * テーマを設定した上で、以下のComposable関数を表示。
     *
     * [jp.mydns.kokoichi0206.countdowntimer.module.main.view.PomodoroView]
     */
    @ExperimentalComposeUiApi
    override fun setPomodoroView() {
        setContent {
            CountDownTimerTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    PomodoroView(
                        presenter = presenter,
                    )
                }
            }
        }
    }

    override fun playFinishSound() {
        soundPoolManager.playFinishSound()
    }

    override fun beginAssembleModules(context: Context): MainContract.Presenter {
        val assembler = MainAssembler()
        return assembler.assembleModules(context)
    }

    override fun beginDisassembleModules() {
        presenter.disassembleModules()
    }

    override fun onDisassemble() {
    }
}
