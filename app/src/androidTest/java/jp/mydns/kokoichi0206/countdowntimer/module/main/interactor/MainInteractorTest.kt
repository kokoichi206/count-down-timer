package jp.mydns.kokoichi0206.countdowntimer.module.main.interactor

import androidx.test.platform.app.InstrumentationRegistry
import jp.mydns.kokoichi0206.countdowntimer.datamanager.DataStoreManager
import jp.mydns.kokoichi0206.countdowntimer.datamanager.MockDataStoreManager
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime

/**
 * メイン画面のInteractorクラスの単体テスト。
 */
class MainInteractorTest {

    lateinit var interactor: MainInteractor
    val mockDataStoreManager: MockDataStoreManager = MockDataStoreManager()

    @Before
    fun setUp() {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val context = instrumentation.targetContext
        interactor = MainInteractor(context, mockDataStoreManager)
        initMock()
    }

    @After
    fun tearDown() {
        initMock()
    }

    fun initMock() {
        mockDataStoreManager.clear()
        assertEquals(
            0,
            mockDataStoreManager.getCount(MockDataStoreManager.MockedMethod.WRITE_STRING)
        )
        assertEquals(
            0,
            mockDataStoreManager.getCount(MockDataStoreManager.MockedMethod.READ_STRING)
        )
    }

    @Test
    fun getTitle() = runBlocking {
        // Arrange
        val title = "This is my timer title"
        mockDataStoreManager.stringValue = title

        // Act
        val result = interactor.getTitle()

        // Assert
        assertEquals(result, title)
        assertEquals(
            1,
            mockDataStoreManager.getCount(MockDataStoreManager.MockedMethod.READ_STRING)
        )
        assertEquals(DataStoreManager.KEY_TIMER_TITLE, mockDataStoreManager.stringKey)
    }

    @Test
    fun getStartedTime() = runBlocking {
        // Arrange
        val startedAtStr = "1970-01-01 09:00:00"
        mockDataStoreManager.stringValue = startedAtStr

        // Act
        val result = interactor.getStartedTime()

        // Assert
        assertEquals(
            result,
            LocalDateTime.of(1970, 1, 1, 9, 0, 0)
        )
        assertEquals(
            1,
            mockDataStoreManager.getCount(MockDataStoreManager.MockedMethod.READ_STRING)
        )
        assertEquals(DataStoreManager.KEY_STARTED_AT, mockDataStoreManager.stringKey)
    }

    @Test
    fun getDeadLine() = runBlocking {
        // Arrange
        val deadline = "1970-01-01 09:00:00"
        mockDataStoreManager.stringValue = deadline

        // Act
        val result = interactor.getDeadLine()

        // Assert
        assertEquals(
            1,
            mockDataStoreManager.getCount(MockDataStoreManager.MockedMethod.READ_STRING)
        )
        assertEquals(DataStoreManager.KEY_DEADLINE, mockDataStoreManager.stringKey)
    }
}
