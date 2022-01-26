package jp.mydns.kokoichi0206.countdowntimer.module.main.view

import android.content.Context
import jp.mydns.kokoichi0206.countdowntimer.module.main.contract.MainContract
import jp.mydns.kokoichi0206.countdowntimer.module.main.presenter.MockMainPresenter

class MainActivityWithMock : MainActivity() {

    private lateinit var mockPresenter: MockMainPresenter

    override fun beginAssembleModules(context: Context): MainContract.Presenter {
        mockPresenter = MockMainPresenter()
        return mockPresenter
    }

    fun getMockPresenter(): MockMainPresenter {
        return mockPresenter
    }
}