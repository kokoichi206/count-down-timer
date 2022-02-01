package jp.mydns.kokoichi0206.countdowntimer.module.main.presenter

import jp.mydns.kokoichi0206.countdowntimer.module.main.contract.MainContract
import jp.mydns.kokoichi0206.countdowntimer.util.MethodCallCounter
import java.time.LocalDateTime

/**
 * メイン画面のPresenterクラスのMock。
 */
class MockMainPresenter : MainContract.Presenter {

    private var counter: MethodCallCounter = MethodCallCounter()

    enum class MockedMethod {
        ON_DATE_TIME_REGISTERED,
        ON_LICENSE_CLICKED,
        ON_PRIVACY_POLICY_CLICKED,
        DISASSEMBLE_MODULES,
    }

    override suspend fun onCreate() {
    }

    override suspend fun onDateTimeRegistered(
        title: String,
        startedAt: LocalDateTime,
        deadLine: LocalDateTime
    ) {
        counter.increment(MockedMethod.ON_DATE_TIME_REGISTERED.name)
    }

    override suspend fun onTitleRegistered(title: String) {
    }

    override fun onLicenseClicked() {
        counter.increment(MockedMethod.ON_LICENSE_CLICKED.name)
    }

    override fun onPrivacyPolicyClicked() {
        counter.increment(MockedMethod.ON_PRIVACY_POLICY_CLICKED.name)
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