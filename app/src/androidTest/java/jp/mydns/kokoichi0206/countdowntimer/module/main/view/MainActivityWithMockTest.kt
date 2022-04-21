package jp.mydns.kokoichi0206.countdowntimer.module.main.view

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import jp.mydns.kokoichi0206.countdowntimer.module.main.presenter.MockMainPresenter
import jp.mydns.kokoichi0206.countdowntimer.util.TestTags
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * メイン画面のViewクラスの単体テスト。
 */
class MainActivityWithMockTest {

    private lateinit var presenter: MockMainPresenter

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @ExperimentalComposeUiApi
    @Before
    fun setUp() {
        presenter = MockMainPresenter()

        composeRule.setContent {
            Home(presenter)
        }
    }

    @After
    fun tearDown() {
    }

    @Test
    fun onDateTimeRegistered() {
        composeRule
            .onNodeWithTag(TestTags.HOME_DISPLAYED_TIME)
            .assertExists()
            .performClick()

        // Not working...
//        composeRule
//            .onNodeWithText(Constants.OK_BUTTON_TEXT)
//            .assertExists()
//            .performClick()
//
//        composeRule
//            .onNodeWithText(Constants.OK_BUTTON_TEXT)
//            .assertExists()
//            .performClick()
    }
}
