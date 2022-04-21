package jp.mydns.kokoichi0206.countdowntimer.module.main.view

import android.content.Context
import jp.mydns.kokoichi0206.countdowntimer.module.main.contract.MainContract
import jp.mydns.kokoichi0206.util.MethodCallCounter
import jp.mydns.kokoichi0206.viper.Presenter
import java.time.LocalDateTime

/**
 * メイン画面のViewクラスのMock。
 */
class MockMainView(
    private val counter: MethodCallCounter = MethodCallCounter()
) : MainContract.View {

    var title: String = ""
    var startedAt: LocalDateTime? = null
    var deadLine: LocalDateTime? = null

    enum class MockedMethod {
        SET_MAIN_VIEW,
        SET_MAIN_VIEW_WITH_TIME,
        SET_POMODORO_VIEW,
        PLAY_FINISH_SOUND,
        ON_DISASSEMBLE,
    }

    override fun setMainView() {
        counter.increment(MockedMethod.SET_MAIN_VIEW.name)
    }

    override fun setMainViewWithTime(
        title: String,
        startedAt: LocalDateTime,
        deadLine: LocalDateTime
    ) {
        counter.increment(MockedMethod.SET_MAIN_VIEW_WITH_TIME.name)
        this.title = title
        this.startedAt = startedAt
        this.deadLine = deadLine
    }

    override fun setPomodoroView() {
        counter.increment(MockedMethod.SET_POMODORO_VIEW.name)
    }

    override fun playFinishSound() {
        counter.increment(MockedMethod.PLAY_FINISH_SOUND.name)
    }

    override fun onDisassemble() {
        counter.increment(MockedMethod.ON_DISASSEMBLE.name)
    }

    override fun beginAssembleModules(context: Context): Presenter {
        TODO("Not yet implemented")
    }

    override fun beginDisassembleModules() {
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
