package jp.mydns.kokoichi0206.countdowntimer.module.main.interactor

import android.content.Context
import jp.mydns.kokoichi0206.countdowntimer.datamanager.DataStoreManager
import jp.mydns.kokoichi0206.countdowntimer.module.main.contract.MainContract
import jp.mydns.kokoichi0206.countdowntimer.util.Constants
import jp.mydns.kokoichi0206.viper.InteractorCallback
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * メイン画面のInteractorクラス。
 */
open class MainInteractor(
    private var context: Context,
    private var dataStoreManager: DataStoreManager = DataStoreManager(),
) : MainContract.Interactor {

    /**
     * InteractorCallbackクラス。
     */
    lateinit var callback: MainContract.InteractorCallback

    /**
     * 外からInteractorCallbackを設定する。
     */
    override fun setInteractorCallback(callback: InteractorCallback) {
        if (callback is MainContract.InteractorCallback) {
            this.callback = callback
        }
    }

    /**
     * DBから前回保存した値（今回の初期値）を読み取る。
     */
    override suspend fun readInitialSettings() {
        val title = getTitle()
        val startedAt = getStartedTime()
        val deadLine = getDeadLine()
        if (startedAt != null && deadLine != null) {
            callback.onReadInitialSettingsCompleted(title, startedAt, deadLine)
        } else {
            callback.onReadInitialSettingsFailed()
        }
    }

    /**
     * DBからタイトルを読み取る。
     *
     * @return タイトル。
     */
    open suspend fun getTitle(): String {
        // キーが存在しない場合 ""（空文字）が返ってくる
        return dataStoreManager.readString(
            context = context,
            key = DataStoreManager.KEY_TIMER_TITLE,
        )
    }

    /**
     * DBからタイトルを読み取る。
     *
     * @return タイマーの開始時刻のLocalDataTime。
     */
    open suspend fun getStartedTime(): LocalDateTime? {
        val startedAtStr = dataStoreManager.readString(
            context = context,
            key = DataStoreManager.KEY_STARTED_AT,
        )
        if (startedAtStr.isBlank()) {
            return null
        }
        val formatter = DateTimeFormatter.ofPattern(Constants.DATETIME_PATTERN)
        return LocalDateTime.parse(startedAtStr, formatter)
    }

    /**
     * DBからタイトルを読み取る。
     *
     * @return タイマーの終了予定時刻のLocalDataTime。
     */
    open suspend fun getDeadLine(): LocalDateTime? {
        val deadLineStr = dataStoreManager.readString(
            context = context,
            key = DataStoreManager.KEY_DEADLINE,
        )
        if (deadLineStr.isBlank()) {
            return null
        }
        val formatter = DateTimeFormatter.ofPattern(Constants.DATETIME_PATTERN)
        return LocalDateTime.parse(deadLineStr, formatter)
    }

    /**
     * DBにタイトルを書き込む。
     */
    override suspend fun writeTitle(title: String) {
        dataStoreManager.writeString(
            context = context,
            key = DataStoreManager.KEY_TIMER_TITLE,
            value = title,
        )
    }

    /**
     * DBにタイマー開始時刻を書き込む。
     *
     * @param[startedAt] 開始時刻のLocalDataTime。
     */
    override suspend fun writeStartedAt(startedAt: LocalDateTime) {
        val formatter = DateTimeFormatter.ofPattern(Constants.DATETIME_PATTERN)
        dataStoreManager.writeString(
            context = context,
            key = DataStoreManager.KEY_STARTED_AT,
            value = startedAt.format(formatter),
        )
    }

    /**
     * DBにタイマー開始時刻を書き込む。
     *
     * @param[deadLine] 終了予定時刻のLocalDataTime。
     */
    override suspend fun writeDeadline(deadLine: LocalDateTime) {
        val formatter = DateTimeFormatter.ofPattern(Constants.DATETIME_PATTERN)
        dataStoreManager.writeString(
            context = context,
            key = DataStoreManager.KEY_DEADLINE,
            value = deadLine.format(formatter),
        )
    }

    override fun onDisassemble() {
    }
}
