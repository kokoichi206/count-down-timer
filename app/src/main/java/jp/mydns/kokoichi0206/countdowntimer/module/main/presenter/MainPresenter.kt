package jp.mydns.kokoichi0206.countdowntimer.module.main.presenter

import jp.mydns.kokoichi0206.countdowntimer.module.main.contract.MainContract
import java.time.LocalDateTime

/**
 * メイン画面のPresenterクラス。
 */
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

    override suspend fun onDateTimeRegistered(
        title: String,
        startedAt: LocalDateTime,
        deadLine: LocalDateTime
    ) {
        // ユーザーが時間の登録を終えた時、
        // DBへの書き込みを開始する。
        interactor?.writeTitle(title)
        interactor?.writeStartedAt(startedAt)
        interactor?.writeDeadline(deadLine)
    }

    override suspend fun onTitleRegistered(title: String) {
        // ユーザーがタイトルの入力を完了した時、
        // タイトルをのDBへの書き込みを開始する。
        interactor?.writeTitle(title)
    }

    override suspend fun onHomeMenuClicked() {
        // ホーム画面メニューがクリックされた時、
        // DBに前回の設定がないか読み込みを開始する。
        interactor?.readInitialSettings()
    }

    override fun onPomodoroMenuClicked() {
        // ポモドーロ画面メニューがクリックされた時、
        // ポモドーロ画面を表示する。
        view?.setPomodoroView()
    }

    override fun onLicenseClicked() {
        // ライセンスメニューがクリックされた時、
        // ライセンス情報を表示させるアクティビティを開始する。
        router?.launchLicenseActivity()
    }

    override fun onPrivacyPolicyClicked() {
        // プライバシーポリシーメニューがクリックされた時、
        // プライバシーポリシー情報を表示させるアクティビティを開始する。
        router?.launchPrivacyPolicyActivity()
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

    override fun onFinishTimer() {
        // タイマーが終了した時、
        // 終了の音源を鳴らす。
        view?.playFinishSound()
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
