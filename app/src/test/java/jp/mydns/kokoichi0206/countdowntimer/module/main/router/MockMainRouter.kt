package jp.mydns.kokoichi0206.countdowntimer.module.main.router

import jp.mydns.kokoichi0206.countdowntimer.module.main.contract.MainContract
import jp.mydns.kokoichi0206.util.MethodCallCounter

class MockMainRouter(
    private val counter: MethodCallCounter = MethodCallCounter()
) : MainContract.Router {

    enum class MockedMethod {
        LAUNCH_LICENSE_ACTIVITY,
        ON_DISASSEMBLE,
    }

    override fun launchLicenseActivity() {
        counter.increment(MockedMethod.LAUNCH_LICENSE_ACTIVITY.name)
    }

    override fun onDisassemble() {
        counter.increment(MockedMethod.ON_DISASSEMBLE.name)
    }

    fun clearMock() {
        counter.clear()
    }

    fun getCount(method: MockedMethod): Int {
        return counter.getCount(method.name)
    }
}