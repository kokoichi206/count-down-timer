package jp.mydns.kokoichi0206.countdowntimer.module.main.view

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import jp.mydns.kokoichi0206.countdowntimer.module.main.presenter.MockMainPresenter
import jp.mydns.kokoichi0206.countdowntimer.util.TestTags
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    private lateinit var presenter: MockMainPresenter

    @ExperimentalComposeUiApi
    @Before
    fun setUp() {
        presenter = MockMainPresenter()
        composeRule.setContent {
            Home(presenter)
        }
        initMock()
    }

    @After
    fun tearDown() {
        initMock()
    }

    fun initMock() {
        presenter.clear()
        assertEquals(0, presenter.getCount(MockMainPresenter.MockedMethod.ON_LICENSE_CLICKED))
    }

    @Test
    fun titleExist() {
        composeRule
            .onNodeWithTag(TestTags.HOME_TITLE)
            .assertExists()
    }

    @Test
    fun timerCircleExist() {
        composeRule
            .onNodeWithTag(TestTags.HOME_CIRCLE)
            .assertExists()
    }

    @Test
    fun timeDisplayed() {
        composeRule
            .onNodeWithTag(TestTags.HOME_DISPLAYED_TIME)
            .assertExists()
    }

    @Test
    fun clickingMoreVertIconShowMenu() {
        // Arrange
        composeRule
            .onNodeWithTag(TestTags.LICENSE_MENU)
            .assertDoesNotExist()

        // Act
        composeRule
            .onNodeWithTag(TestTags.MORE_VERT_ICON)
            .assertExists()
            .performClick()

        // Assert
        // License menu exists after clicking moreVert icon
        composeRule
            .onNodeWithTag(TestTags.LICENSE_MENU)
            .assertExists()
    }

    @Test
    fun onLicenseClicked() {
        // Arrange
        // expand the menu in the more_vert icon
        composeRule
            .onNodeWithTag(TestTags.MORE_VERT_ICON)
            .assertExists()
            .performClick()

        // Act
        composeRule
            .onNodeWithTag(TestTags.LICENSE_MENU)
            .assertExists()
            .performClick()

        // Assert
        assertEquals(1, presenter.getCount(MockMainPresenter.MockedMethod.ON_LICENSE_CLICKED))
    }
}