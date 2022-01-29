package jp.mydns.kokoichi0206.countdowntimer.module.main.presenter

import jp.mydns.kokoichi0206.countdowntimer.module.main.contract.MainContract
import java.time.LocalDateTime

class MainPresenter(
    var view: MainContract.View?,
    var interactor: MainContract.Interactor?,
    var router: MainContract.Router?,
) : MainContract.Presenter, MainContract.InteractorCallback {

    override suspend fun onCreate() {
        // 画面が生成された時、
        // DBに前回の設定がないか読み込みを開始する。
        interactor?.readInitialSettings()
    }

    override suspend fun onDateTimeRegistered(title: String, startedAt: LocalDateTime, deadLine: LocalDateTime) {
        // ユーザーが時間の登録を終えた時、
        // DBへの書き込みを開始する。
        interactor?.writeStartedAt(startedAt)
        interactor?.writeDeadline(deadLine)
    }

    override suspend fun onTitleRegistered(title: String) {
    }

    override fun onReadInitialSettingsCompleted(
        title: String,
        startedAt: LocalDateTime,
        deadLine: LocalDateTime
    ) {
        // DBの前回設定の読み込みが完了した時、
        // それをもとに画面を表示させる。
        view?.setMainViewWithTime(title = title, startedAt = startedAt, deadLine = deadLine)
    }

    override fun onReadInitialSettingsFailed() {
        // DBの前回設定が読み込み出来なかった時、
        // デフォルトのスタート画面を表示させる。
        view?.setMainView()
    }

    override fun disassembleModules() {
        view?.let {
            it.onDisassemble()
            view = null
        }
        interactor?.let {
            it.onDisassemble()
            interactor = null
        }
        router?.let {
            it.onDisassemble()
            router = null
        }
    }
}