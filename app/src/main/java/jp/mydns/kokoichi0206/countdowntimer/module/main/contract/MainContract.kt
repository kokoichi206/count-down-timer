package jp.mydns.kokoichi0206.countdowntimer.module.main.contract

import java.time.LocalDateTime

/**
 * メイン画面のContractクラス。
 */
class MainContract {
    /**
     * Viewクラスの定義。
     */
    interface View : jp.mydns.kokoichi0206.viper.View {
        fun setMainView()

        fun setMainViewWithTime(
            title: String,
            startedAt: LocalDateTime,
            deadLine: LocalDateTime,
        )

        fun setPomodoroView()

        fun playFinishSound()
    }

    /**
     * Interactorクラスの定義。
     */
    interface Interactor : jp.mydns.kokoichi0206.viper.Interactor {
        suspend fun readInitialSettings()

        suspend fun writeTitle(title: String)

        suspend fun writeStartedAt(startedAt: LocalDateTime)

        suspend fun writeDeadline(deadLine: LocalDateTime)
    }

    /**
     * InteractorCallbackクラスの定義。
     */
    interface InteractorCallback : jp.mydns.kokoichi0206.viper.InteractorCallback {
        fun onReadInitialSettingsCompleted(
            title: String,
            startedAt: LocalDateTime,
            deadLine: LocalDateTime
        )

        fun onReadInitialSettingsFailed()
    }

    /**
     * Routerクラスの定義。
     */
    interface Router : jp.mydns.kokoichi0206.viper.Router {
        fun launchLicenseActivity()

        fun launchPrivacyPolicyActivity()
    }

    /**
     * Presenterクラスの定義。
     */
    interface Presenter : jp.mydns.kokoichi0206.viper.Presenter {
        suspend fun onCreate()

        suspend fun onDateTimeRegistered(
            title: String,
            startedAt: LocalDateTime,
            deadLine: LocalDateTime
        )

        suspend fun onTitleRegistered(title: String)

        suspend fun onHomeMenuClicked()

        fun onPomodoroMenuClicked()

        fun onLicenseClicked()

        fun onPrivacyPolicyClicked()

        fun onFinishTimer()
    }
}
