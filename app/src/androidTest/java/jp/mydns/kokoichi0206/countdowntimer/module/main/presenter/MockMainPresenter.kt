package jp.mydns.kokoichi0206.countdowntimer.module.main.presenter

import jp.mydns.kokoichi0206.countdowntimer.module.main.contract.MainContract
import jp.mydns.kokoichi0206.countdowntimer.util.MethodCallCounter
import java.time.LocalDateTime

class MockMainPresenter : MainContract.Presenter {

    private var counter: MethodCallCounter = MethodCallCounter()

    enum class MockedMethod {
        ON_DATE_TIME_REGISTERED,
        DISASSEMBLE_MODULES,
    }

    override fun onDateTimeRegistered(startedAt: LocalDateTime, deadLine: LocalDateTime) {
        counter.increment(MockedMethod.ON_DATE_TIME_REGISTERED.name)
    }

    override fun disassembleModules() {
        counter.increment(MockedMethod.DISASSEMBLE_MODULES.name)
    }

    fun clear() {
        counter.clear()
    }

    fun getCount(method: MockedMethod): Int {
        return counter.getCount(method.name)
    }
}