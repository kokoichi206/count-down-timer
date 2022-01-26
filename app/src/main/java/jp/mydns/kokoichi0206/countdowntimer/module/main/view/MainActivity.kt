package jp.mydns.kokoichi0206.countdowntimer.module.main.view

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.ExperimentalComposeUiApi
import jp.mydns.kokoichi0206.countdowntimer.module.main.assembler.MainAssembler
import jp.mydns.kokoichi0206.countdowntimer.module.main.contract.MainContract
import jp.mydns.kokoichi0206.countdowntimer.ui.theme.CountDownTimerTheme

class MainActivity : ComponentActivity(), MainContract.View {
    lateinit var presenter: MainContract.Presenter

    @RequiresApi(Build.VERSION_CODES.O)
    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = beginAssembleModules(this)
        setContent {
            CountDownTimerTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Home()
                }
            }
        }
    }

    override fun onDisassemble() {
        TODO("Not yet implemented")
    }

    override fun beginAssembleModules(context: Context): MainContract.Presenter {
        val assembler = MainAssembler()
        return assembler.assembleModules(context)
    }

    override fun beginDisassembleModules() {
        TODO("Not yet implemented")
    }
}
