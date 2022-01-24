package jp.mydns.kokoichi0206.countdowntimer.module.main.presenter

import jp.mydns.kokoichi0206.countdowntimer.module.main.contract.MainContract
import jp.mydns.kokoichi0206.countdowntimer.module.main.interactor.MainInteractor

class MainPresenter(
    view: MainContract.View,
    interactor: MainContract.Interactor,
    router: MainContract.Router
) : MainContract.Presenter, MainContract.InteractorCallback {

    override fun disassembleModules() {

    }
}