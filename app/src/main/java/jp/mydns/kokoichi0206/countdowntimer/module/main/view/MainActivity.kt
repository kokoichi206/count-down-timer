package jp.mydns.kokoichi0206.countdowntimer.module.main.view

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.ExperimentalComposeUiApi
import jp.mydns.kokoichi0206.countdowntimer.module.main.assembler.MainAssembler
import jp.mydns.kokoichi0206.countdowntimer.module.main.contract.MainContract
import jp.mydns.kokoichi0206.countdowntimer.ui.theme.CountDownTimerTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.time.LocalDateTime

open class MainActivity : ComponentActivity(), MainContract.View {
    lateinit var presenter: MainContract.Presenter

    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = beginAssembleModules(this)
        runBlocking {
            withContext(Dispatchers.IO) {
                presenter.onCreate()
            }
        }
    }

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

    @ExperimentalComposeUiApi
    override fun setMainViewWithTime(startedAt: LocalDateTime, deadLine: LocalDateTime) {
        setContent {
            CountDownTimerTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Home(
                        presenter = presenter,
                        startedTime = startedAt,
                        deadline = deadLine,
                    )
                }
            }
        }
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
