package jp.mydns.kokoichi0206.countdowntimer.module.main.interactor

import androidx.test.platform.app.InstrumentationRegistry
import jp.mydns.kokoichi0206.countdowntimer.datamanager.DataStoreManager
import jp.mydns.kokoichi0206.countdowntimer.datamanager.MockDataStoreManager
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * メイン画面のInteractorクラスの単体テスト。
 */
class MainInteractorWithMockTest {

    lateinit var interactor: MainInteractorWithMock
    lateinit var mockDataStoreManager: MockDataStoreManager
    val mockInteractorCallback: MockInteractorCallback = MockInteractorCallback()

    @Before
    fun setUp() {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val context = instrumentation.targetContext
        interactor = MainInteractorWithMock(context)
        mockDataStoreManager = interactor.getMockDataStoreManage()
        interactor.setInteractorCallback(mockInteractorCallback)
        initMock()
    }

    @After
    fun tearDown() {
        initMock()
    }

    fun initMock() {
        interactor.clearMock()
        mockInteractorCallback.clearMock()
        mockDataStoreManager.clear()
        assertEquals(
            0,
            interactor.getCount(MainInteractorWithMock.MockedMethod.GET_TITLE)
        )
        assertEquals(
            0,
            interactor.getCount(MainInteractorWithMock.MockedMethod.GET_STARTED_TIME)
        )
        assertEquals(
            0,
            interactor.getCount(MainInteractorWithMock.MockedMethod.GET_DEAD_LINE)
        )
        assertEquals(
            0,
            mockInteractorCallback.getCount(MockInteractorCallback.MockedMethod.ON_READ_INITIAL_SETTINGS_COMPLETED)
        )
        assertEquals(
            0,
            mockInteractorCallback.getCount(MockInteractorCallback.MockedMethod.ON_READ_INITIAL_SETTINGS_FAILED)
        )
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
    fun readInitialSettings() = runBlocking {
        // Arrange
        val title = "This is my title"
        val startedAt = LocalDateTime.of(1970, 1, 1, 9, 11)
        val deadLine = LocalDateTime.of(1974, 3, 3, 5, 34)
        interactor.title = title
        interactor.startedAt = startedAt
        interactor.deadLine = deadLine

        // Act
        interactor.readInitialSettings()

        // Assert
        assertEquals(
            1,
            interactor.getCount(MainInteractorWithMock.MockedMethod.GET_TITLE)
        )
        assertEquals(
            1,
            interactor.getCount(MainInteractorWithMock.MockedMethod.GET_STARTED_TIME)
        )
        assertEquals(
            1,
            interactor.getCount(MainInteractorWithMock.MockedMethod.GET_DEAD_LINE)
        )
        assertEquals(
            1,
            mockInteractorCallback.getCount(MockInteractorCallback.MockedMethod.ON_READ_INITIAL_SETTINGS_COMPLETED)
        )
        assertEquals(
            0,
            mockInteractorCallback.getCount(MockInteractorCallback.MockedMethod.ON_READ_INITIAL_SETTINGS_FAILED)
        )
        assertEquals(title, mockInteractorCallback.title)
        assertEquals(startedAt, mockInteractorCallback.startedAt)
        assertEquals(deadLine, mockInteractorCallback.deadLine)
    }

    @Test
    fun readInitialSettingsWithNullStartedAt() = runBlocking {
        // Arrange
        val startedAt = LocalDateTime.of(1974, 3, 3, 5, 34)
        interactor.startedAt = startedAt
        interactor.deadLine = null

        // Act
        interactor.readInitialSettings()

        // Assert
        assertEquals(
            1,
            interactor.getCount(MainInteractorWithMock.MockedMethod.GET_TITLE)
        )
        assertEquals(
            1,
            interactor.getCount(MainInteractorWithMock.MockedMethod.GET_STARTED_TIME)
        )
        assertEquals(
            1,
            interactor.getCount(MainInteractorWithMock.MockedMethod.GET_DEAD_LINE)
        )
        assertEquals(
            0,
            mockInteractorCallback.getCount(MockInteractorCallback.MockedMethod.ON_READ_INITIAL_SETTINGS_COMPLETED)
        )
        assertEquals(
            1,
            mockInteractorCallback.getCount(MockInteractorCallback.MockedMethod.ON_READ_INITIAL_SETTINGS_FAILED)
        )
    }

    @Test
    fun readInitialSettingsWithNullDeadLine() = runBlocking {
        // Arrange
        val deadLine = LocalDateTime.of(1974, 3, 3, 5, 34)
        interactor.startedAt = null
        interactor.deadLine = deadLine

        // Act
        interactor.readInitialSettings()

        // Assert
        assertEquals(
            1,
            interactor.getCount(MainInteractorWithMock.MockedMethod.GET_TITLE)
        )
        assertEquals(
            1,
            interactor.getCount(MainInteractorWithMock.MockedMethod.GET_STARTED_TIME)
        )
        assertEquals(
            1,
            interactor.getCount(MainInteractorWithMock.MockedMethod.GET_DEAD_LINE)
        )
        assertEquals(
            0,
            mockInteractorCallback.getCount(MockInteractorCallback.MockedMethod.ON_READ_INITIAL_SETTINGS_COMPLETED)
        )
        assertEquals(
            1,
            mockInteractorCallback.getCount(MockInteractorCallback.MockedMethod.ON_READ_INITIAL_SETTINGS_FAILED)
        )
    }

    @Test
    fun readInitialSettingsWithNoInitialValue() = runBlocking {
        // Arrange
        interactor.title = "Something"
        interactor.startedAt = null
        interactor.deadLine = null

        // Act
        interactor.readInitialSettings()

        // Assert
        assertEquals(
            1,
            interactor.getCount(MainInteractorWithMock.MockedMethod.GET_TITLE)
        )
        assertEquals(
            1,
            interactor.getCount(MainInteractorWithMock.MockedMethod.GET_STARTED_TIME)
        )
        assertEquals(
            1,
            interactor.getCount(MainInteractorWithMock.MockedMethod.GET_DEAD_LINE)
        )
        assertEquals(
            0,
            mockInteractorCallback.getCount(MockInteractorCallback.MockedMethod.ON_READ_INITIAL_SETTINGS_COMPLETED)
        )
        assertEquals(
            1,
            mockInteractorCallback.getCount(MockInteractorCallback.MockedMethod.ON_READ_INITIAL_SETTINGS_FAILED)
        )
    }

    @Test
    fun writeTitle() = runBlocking {
        // Arrange
        val title = "This is my title"

        // Act
        interactor.writeTitle(title)

        // Assert
        assertEquals(
            1,
            mockDataStoreManager.getCount(MockDataStoreManager.MockedMethod.WRITE_STRING)
        )
        assertEquals(DataStoreManager.KEY_TIMER_TITLE, mockDataStoreManager.stringKey)
        assertEquals(title, mockDataStoreManager.stringValue)
    }

    @Test
    fun writeStartedAt() = runBlocking {
        // Arrange
        val startedAtStr = "1970-01-01 09:00:00"
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val startedAt = LocalDateTime.parse(startedAtStr, formatter)

        // Act
        interactor.writeStartedAt(startedAt)

        // Assert
        assertEquals(
            1,
            mockDataStoreManager.getCount(MockDataStoreManager.MockedMethod.WRITE_STRING)
        )
        assertEquals(DataStoreManager.KEY_STARTED_AT, mockDataStoreManager.stringKey)
        assertEquals(startedAtStr, mockDataStoreManager.stringValue)
    }

    @Test
    fun writeDeadline() = runBlocking {
        // Arrange
        val deadlineStr = "1970-01-01 09:00:21"
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val deadline = LocalDateTime.parse(deadlineStr, formatter)

        // Act
        interactor.writeDeadline(deadline)

        // Assert
        assertEquals(
            1,
            mockDataStoreManager.getCount(MockDataStoreManager.MockedMethod.WRITE_STRING)
        )
        assertEquals(DataStoreManager.KEY_DEADLINE, mockDataStoreManager.stringKey)
        assertEquals(deadlineStr, mockDataStoreManager.stringValue)
    }
}