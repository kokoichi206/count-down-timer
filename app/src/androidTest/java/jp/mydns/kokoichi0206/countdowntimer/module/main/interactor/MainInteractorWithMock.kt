package jp.mydns.kokoichi0206.countdowntimer.module.main.interactor

import android.content.Context
import jp.mydns.kokoichi0206.countdowntimer.datamanager.MockDataStoreManager
import jp.mydns.kokoichi0206.countdowntimer.util.MethodCallCounter
import java.time.LocalDateTime

class MainInteractorWithMock(
    var context: Context,
    var mockDataStoreManager: MockDataStoreManager = MockDataStoreManager(),
    var counter: MethodCallCounter = MethodCallCounter(),
) : MainInteractor(context, mockDataStoreManager) {

    var title: String = ""
    var startedAt: LocalDateTime? = null
    var deadLine: LocalDateTime? = null

    enum class MockedMethod {
        GET_TITLE,
        GET_STARTED_TIME,
        GET_DEAD_LINE,
    }

    override suspend fun getTitle(): String {
        counter.increment(MockedMethod.GET_TITLE.name)
        return title
    }

    override suspend fun getStartedTime(): LocalDateTime? {
        counter.increment(MockedMethod.GET_STARTED_TIME.name)
        return startedAt
    }

    override suspend fun getDeadLine(): LocalDateTime? {
        counter.increment(MockedMethod.GET_DEAD_LINE.name)
        return deadLine
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

    fun getMockDataStoreManage(): MockDataStoreManager {
        return mockDataStoreManager
    }
}