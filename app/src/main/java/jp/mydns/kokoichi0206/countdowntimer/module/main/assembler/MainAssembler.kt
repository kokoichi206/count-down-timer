package jp.mydns.kokoichi0206.countdowntimer.module.main.assembler

import android.app.Activity
import android.content.Context
import jp.mydns.kokoichi0206.countdowntimer.module.main.contract.MainContract
import jp.mydns.kokoichi0206.countdowntimer.module.main.interactor.MainInteractor
import jp.mydns.kokoichi0206.countdowntimer.module.main.presenter.MainPresenter
import jp.mydns.kokoichi0206.countdowntimer.module.main.router.MainRouter

class MainAssembler : jp.mydns.kokoichi0206.viper.Assembler {
    override fun assembleModules(context: Context): MainContract.Presenter {
        lateinit var presenter: MainPresenter
        lateinit var view: MainContract.View
        lateinit var interactor: MainContract.Interactor
        lateinit var router: MainContract.Router
        interactor = MainInteractor(context)
        if (context is Activity) {
            router = MainRouter(context)
        }
        if (context is MainContract.View) {
            view = context
        }
        presenter = MainPresenter(view, interactor, router)
        interactor.setInteractorCallback(presenter)
        return presenter
    }
}