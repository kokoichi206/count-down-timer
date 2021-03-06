package jp.mydns.kokoichi0206.countdowntimer.module.main.interactor

import jp.mydns.kokoichi0206.countdowntimer.module.main.contract.MainContract
import jp.mydns.kokoichi0206.util.MethodCallCounter
import jp.mydns.kokoichi0206.viper.InteractorCallback
import java.time.LocalDateTime

/**
 * メイン画面のInteractorクラスのMock。
 */
class MockMainInteractor(
    private val counter: MethodCallCounter = MethodCallCounter()
) : MainContract.Interactor {

    var title: String = ""
    var startedAt: LocalDateTime? = null
    var deadLine: LocalDateTime? = null

    enum class MockedMethod {
        READ_INITIAL_SETTINGS,
        WRITE_TITLE,
        WRITE_STARTED_AT,
        WRITE_DEADLINE,
        ON_DISASSEMBLE,
    }

    override suspend fun readInitialSettings() {
        counter.increment(MockedMethod.READ_INITIAL_SETTINGS.name)
    }

    override suspend fun writeTitle(title: String) {
        counter.increment(MockedMethod.WRITE_TITLE.name)
        this.title = title
    }

    override suspend fun writeStartedAt(startedAt: LocalDateTime) {
        counter.increment(MockedMethod.WRITE_STARTED_AT.name)
        this.startedAt = startedAt
    }

    override suspend fun writeDeadline(deadLine: LocalDateTime) {
        counter.increment(MockedMethod.WRITE_DEADLINE.name)
        this.deadLine = deadLine
    }

    override fun setInteractorCallback(callback: InteractorCallback) {
    }

    override fun onDisassemble() {
        counter.increment(MockedMethod.ON_DISASSEMBLE.name)
    }

    fun clearMock() {
        counter.clear()
        title = ""
        startedAt = null
        deadLine = null
    }

    fun getCount(method: MockedMethod): Int {
        return counter.getCount(method.name)
    }
}
