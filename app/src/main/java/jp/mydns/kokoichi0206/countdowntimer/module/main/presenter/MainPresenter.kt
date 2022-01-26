package jp.mydns.kokoichi0206.countdowntimer.module.main.presenter

import jp.mydns.kokoichi0206.countdowntimer.module.main.contract.MainContract
import java.time.LocalDateTime

class MainPresenter(
    view: MainContract.View,
    interactor: MainContract.Interactor,
    router: MainContract.Router
) : MainContract.Presenter, MainContract.InteractorCallback {

    override fun onDateTimeRegistered(startedAt: LocalDateTime, deadLine: LocalDateTime) {
    }

    override fun disassembleModules() {

    }
}