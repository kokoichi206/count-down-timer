package jp.mydns.kokoichi0206.countdowntimer.module.main.contract

import java.time.LocalDateTime

class MainContract {

    interface View : jp.mydns.kokoichi0206.viper.View {
    }

    interface Interactor : jp.mydns.kokoichi0206.viper.Interactor {
    }

    interface InteractorCallback : jp.mydns.kokoichi0206.viper.InteractorCallback {
    }

    interface Router : jp.mydns.kokoichi0206.viper.Router {
    }

    interface Presenter : jp.mydns.kokoichi0206.viper.Presenter {
        fun onDateTimeRegistered(startedAt: LocalDateTime, deadLine: LocalDateTime)
    }
}