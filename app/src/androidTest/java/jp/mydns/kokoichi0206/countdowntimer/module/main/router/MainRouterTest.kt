package jp.mydns.kokoichi0206.countdowntimer.module.main.router

import android.app.Instrumentation
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import jp.mydns.kokoichi0206.countdowntimer.module.main.view.MainActivity
import jp.mydns.kokoichi0206.countdowntimer.module.menu.view.PrivacyPolicyActivity
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * メイン画面のRouterクラスの単体テスト。
 */
class MainRouterTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    private lateinit var router: MainRouter

    @Before
    fun setUp() {
        router = MainRouter(composeRule.activity)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun launchLicenseActivity() {
        // Arrange
        val instrumentationRegistry = InstrumentationRegistry.getInstrumentation()

        val monitor =
            Instrumentation.ActivityMonitor(OssLicensesMenuActivity::class.java.name, null, false)
        instrumentationRegistry.addMonitor(monitor)
        assertFalse(instrumentationRegistry.checkMonitorHit(monitor, 1))

        // Act
        router.launchLicenseActivity()

        // Assert
        assertTrue(instrumentationRegistry.checkMonitorHit(monitor, 1))
    }

    @Test
    fun launchPrivacyPolicyActivity() {
        // Arrange
        val instrumentationRegistry = InstrumentationRegistry.getInstrumentation()

        val monitor =
            Instrumentation.ActivityMonitor(PrivacyPolicyActivity::class.java.name, null, false)
        instrumentationRegistry.addMonitor(monitor)
        assertFalse(instrumentationRegistry.checkMonitorHit(monitor, 1))

        // Act
        router.launchPrivacyPolicyActivity()

        // Assert
        assertTrue(instrumentationRegistry.checkMonitorHit(monitor, 1))
    }
}
