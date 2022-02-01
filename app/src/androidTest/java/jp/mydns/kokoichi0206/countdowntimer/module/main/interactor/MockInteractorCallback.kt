package jp.mydns.kokoichi0206.countdowntimer.module.main.interactor

import jp.mydns.kokoichi0206.countdowntimer.module.main.contract.MainContract
import jp.mydns.kokoichi0206.countdowntimer.util.MethodCallCounter
import java.time.LocalDateTime

/**
 * メイン画面のInteractorCallbackクラスのMock。
 */
class MockInteractorCallback(
    val counter: MethodCallCounter = MethodCallCounter(),
) : MainContract.InteractorCallback {

    var title: String? = null
    var startedAt: LocalDateTime? = null
    var deadLine: LocalDateTime? = null

    enum class MockedMethod {
        ON_READ_INITIAL_SETTINGS_COMPLETED,
        ON_READ_INITIAL_SETTINGS_FAILED,
    }

    override fun onReadInitialSettingsCompleted(
        title: String,
        startedAt: LocalDateTime,
        deadLine: LocalDateTime
    ) {
        this.title = title
        this.startedAt = startedAt
        this.deadLine = deadLine
        counter.increment(MockedMethod.ON_READ_INITIAL_SETTINGS_COMPLETED.name)
    }

    override fun onReadInitialSettingsFailed() {
        counter.increment(MockedMethod.ON_READ_INITIAL_SETTINGS_FAILED.name)
    }

    fun clearMock() {
        counter.clear()
        title = null
        startedAt = null
        deadLine = null
    }

    fun getCount(method: MockedMethod): Int {
        return counter.getCount(method.name)
    }
}