package jp.mydns.kokoichi0206.countdowntimer.module.main.presenter

import jp.mydns.kokoichi0206.countdowntimer.module.main.interactor.MockMainInteractor
import jp.mydns.kokoichi0206.countdowntimer.module.main.router.MockMainRouter
import jp.mydns.kokoichi0206.countdowntimer.module.main.view.MockMainView
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainPresenterTest {

    lateinit var presenter: MainPresenter

    lateinit var mockView: MockMainView
    lateinit var mockInteractor: MockMainInteractor
    lateinit var mockRouter: MockMainRouter

    @Before
    fun setUp() {
        mockView = MockMainView()
        mockInteractor = MockMainInteractor()
        mockRouter = MockMainRouter()
        presenter = MainPresenter(mockView, mockInteractor, mockRouter)
        initMock()
    }

    @After
    fun tearDown() {
    }

    fun initMock() {
        mockView.clearMock()
        mockInteractor.clearMock()
        mockRouter.clearMock()
        assertEquals(
            0,
            mockView.getCount(MockMainView.MockedMethod.SET_MAIN_VIEW)
        )
        assertEquals(
            0,
            mockView.getCount(MockMainView.MockedMethod.SET_MAIN_VIEW_WITH_TIME)
        )
        assertEquals(
            0,
            mockView.getCount(MockMainView.MockedMethod.ON_DISASSEMBLE)
        )
        assertEquals(
            0,
            mockInteractor.getCount(MockMainInteractor.MockedMethod.READ_INITIAL_SETTINGS)
        )
        assertEquals(
            0,
            mockInteractor.getCount(MockMainInteractor.MockedMethod.WRITE_STARTED_AT)
        )
        assertEquals(
            0,
            mockInteractor.getCount(MockMainInteractor.MockedMethod.WRITE_DEADLINE)
        )
        assertEquals(
            0,
            mockInteractor.getCount(MockMainInteractor.MockedMethod.ON_DISASSEMBLE)
        )
        assertEquals(
            0,
            mockRouter.getCount(MockMainRouter.MockedMethod.LAUNCH_LICENSE_ACTIVITY)
        )
        assertEquals(
            0,
            mockRouter.getCount(MockMainRouter.MockedMethod.ON_DISASSEMBLE)
        )
    }

    @Test
    fun onCreate() = runBlocking {
        // Arrange

        // Act
        presenter.onCreate()

        // Assert
        assertEquals(
            1,
            mockInteractor.getCount(MockMainInteractor.MockedMethod.READ_INITIAL_SETTINGS)
        )
    }

    @Test
    fun onDateTimeRegistered() = runBlocking {
        // Arrange
        val title = "FINISH HOMEWORK"
        val startedAtStr = "1970-01-01 09:00:00"
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val startedAt = LocalDateTime.parse(startedAtStr, formatter)
        val deadLineStr = "1970-01-01 09:00:00"
        val deadLine = LocalDateTime.parse(deadLineStr, formatter)

        // Act
        presenter.onDateTimeRegistered(title, startedAt, deadLine)

        // Assert
        assertEquals(
            1,
            mockInteractor.getCount(MockMainInteractor.MockedMethod.WRITE_STARTED_AT)
        )
        assertEquals(
            1,
            mockInteractor.getCount(MockMainInteractor.MockedMethod.WRITE_STARTED_AT)
        )
        assertEquals(
            1,
            mockInteractor.getCount(MockMainInteractor.MockedMethod.WRITE_DEADLINE)
        )
    }

    @Test
    fun onTitleRegistered() = runBlocking {
        // Arrange
        val title = "Title"

        // Act
        presenter.onTitleRegistered(title)

        // Assert
        assertEquals(
            1,
            mockInteractor.getCount(MockMainInteractor.MockedMethod.WRITE_TITLE)
        )
    }

    @Test
    fun onLicenseClicked() {
        // Arrange

        // Act
        presenter.onLicenseClicked()

        // Assert
        assertEquals(
            1,
            mockRouter.getCount(MockMainRouter.MockedMethod.LAUNCH_LICENSE_ACTIVITY)
        )
    }

    @Test
    fun onPrivacyPolicyClicked() {
        // Arrange

        // Act
        presenter.onPrivacyPolicyClicked()

        // Assert
        assertEquals(
            1,
            mockRouter.getCount(MockMainRouter.MockedMethod.LAUNCH_PRIVACY_POLICY_ACTIVITY)
        )
    }

    @Test
    fun onReadInitialSettingsCompleted() {
        // Arrange
        val title = "title"
        val startedAtStr = "1970-01-01 09:00:00"
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val startedAt = LocalDateTime.parse(startedAtStr, formatter)
        val deadLineStr = "1970-01-01 09:00:00"
        val deadLine = LocalDateTime.parse(deadLineStr, formatter)

        // Act
        presenter.onReadInitialSettingsCompleted(title, startedAt, deadLine)

        // Assert
        assertEquals(
            1,
            mockView.getCount(MockMainView.MockedMethod.SET_MAIN_VIEW_WITH_TIME)
        )
        assertEquals(title, mockView.title)
        assertEquals(startedAt, mockView.startedAt)
        assertEquals(startedAt, mockView.startedAt)
    }

    @Test
    fun onReadInitialSettingsFailed() {
        // Arrange

        // Act
        presenter.onReadInitialSettingsFailed()

        // Assert
        assertEquals(
            1,
            mockView.getCount(MockMainView.MockedMethod.SET_MAIN_VIEW)
        )
    }

    @Test
    fun disassembleModules() {
        // Arrange

        // Act
        presenter.disassembleModules()

        // Assert
        assertEquals(
            1,
            mockView.getCount(MockMainView.MockedMethod.ON_DISASSEMBLE)
        )
        assertEquals(
            1,
            mockInteractor.getCount(MockMainInteractor.MockedMethod.ON_DISASSEMBLE)
        )
        assertEquals(
            1,
            mockRouter.getCount(MockMainRouter.MockedMethod.ON_DISASSEMBLE)
        )
    }
}