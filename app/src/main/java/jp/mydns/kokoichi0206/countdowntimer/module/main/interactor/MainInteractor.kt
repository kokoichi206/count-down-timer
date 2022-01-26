package jp.mydns.kokoichi0206.countdowntimer.module.main.interactor

import android.content.Context
import jp.mydns.kokoichi0206.countdowntimer.module.main.contract.MainContract
import jp.mydns.kokoichi0206.viper.InteractorCallback

class MainInteractor(context: Context) : MainContract.Interactor {

    lateinit var callback: MainContract.InteractorCallback

    override fun setInteractorCallback(callback: InteractorCallback) {
        if(callback is MainContract.InteractorCallback) {
            this.callback = callback
        }
    }


    override fun onDisassemble() {
    }
}