package jp.mydns.kokoichi0206.countdowntimer.module.main.interactor

import android.content.Context
import jp.mydns.kokoichi0206.countdowntimer.datamanager.DataStoreManager
import jp.mydns.kokoichi0206.countdowntimer.module.main.contract.MainContract
import jp.mydns.kokoichi0206.countdowntimer.util.Constants
import jp.mydns.kokoichi0206.viper.InteractorCallback
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

open class MainInteractor(
    private var context: Context,
    private var dataStoreManager: DataStoreManager = DataStoreManager(),
) : MainContract.Interactor {

    lateinit var callback: MainContract.InteractorCallback

    override fun setInteractorCallback(callback: InteractorCallback) {
        if (callback is MainContract.InteractorCallback) {
            this.callback = callback
        }
    }

    override suspend fun readInitialSettings() {
        val startedAt = getStartedTime()
        val deadLine = getDeadLine()
        if (startedAt != null && deadLine != null) {
            callback.onReadInitialSettingsCompleted(startedAt, deadLine)
        } else {
            callback.onReadInitialSettingsFailed()
        }
    }

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

    override suspend fun writeStartedAt(startedAt: LocalDateTime) {
        dataStoreManager.writeString(
            context = context,
            key = DataStoreManager.KEY_STARTED_AT,
            value = startedAt.toString(),
        )
    }

    override suspend fun writeDeadline(deadLine: LocalDateTime) {
        dataStoreManager.writeString(
            context = context,
            key = DataStoreManager.KEY_DEADLINE,
            value = deadLine.toString(),
        )
    }

    override fun onDisassemble() {
    }
}