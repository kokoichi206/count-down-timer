package jp.mydns.kokoichi0206.countdowntimer.module.main.contract

import java.time.LocalDateTime

class MainContract {

    interface View : jp.mydns.kokoichi0206.viper.View {
        fun setMainView()

        fun setMainViewWithTime(
            title: String,
            startedAt: LocalDateTime,
            deadLine: LocalDateTime,
        )
    }

    interface Interactor : jp.mydns.kokoichi0206.viper.Interactor {
        suspend fun readInitialSettings()

        suspend fun writeTitle(title: String)

        suspend fun writeStartedAt(startedAt: LocalDateTime)

        suspend fun writeDeadline(deadLine: LocalDateTime)
    }

    interface InteractorCallback : jp.mydns.kokoichi0206.viper.InteractorCallback {
        fun onReadInitialSettingsCompleted(
            title: String,
            startedAt: LocalDateTime,
            deadLine: LocalDateTime
        )

        fun onReadInitialSettingsFailed()
    }

    interface Router : jp.mydns.kokoichi0206.viper.Router {
    }

    interface Presenter : jp.mydns.kokoichi0206.viper.Presenter {
        suspend fun onCreate()

        suspend fun onDateTimeRegistered(
            title: String,
            startedAt: LocalDateTime,
            deadLine: LocalDateTime
        )

        suspend fun onTitleRegistered(title: String)

        fun onLicenseClicked()
    }
}