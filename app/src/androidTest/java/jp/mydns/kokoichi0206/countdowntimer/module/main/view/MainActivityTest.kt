package jp.mydns.kokoichi0206.countdowntimer.module.main.view

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import jp.mydns.kokoichi0206.countdowntimer.module.main.presenter.MockMainPresenter
import jp.mydns.kokoichi0206.countdowntimer.util.TestTags
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @ExperimentalComposeUiApi
    @Before
    fun setUp() {
        val presenter = MockMainPresenter()
        composeRule.setContent {
            Home(presenter)
        }
    }

    @After
    fun tearDown() {
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
}