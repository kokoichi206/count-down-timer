``` plantuml
@startuml

/'
メソッドなどの各メンバーの記載は省略する
'/
namespace jp.mydns.kokoichi0206.viper #CCCCCC {
    interface View
    interface Router
    interface Interactor
    interface InteractorCallback
    interface Presenter
    interface Assembler
}

package jp.mydns.kokoichi0206.countdowntimer.module.Main {
    package contract {
        class MainContract
        interface View extends jp.mydns.kokoichi0206.viper.View
        interface Router extends jp.mydns.kokoichi0206.viper.Router
        interface Interactor extends jp.mydns.kokoichi0206.viper.Interactor
        interface InteractorCallback extends jp.mydns.kokoichi0206.viper.InteractorCallback
        interface Presenter extends jp.mydns.kokoichi0206.viper.Presenter
    }

    package view {
        class MainActivity #88FF88 implements View
    }

    package router {
        class MainRouter #88FF88 implements Router
    }

    package interactor {
        class MainInteractor #88FF88 implements Interactor
    }

    package presenter {
        class MainPresenter #88FF88 implements Presenter
    }

    package assembler {
        class MainAssembler #88FF88 implements jp.mydns.kokoichi0206.viper.Assembler
    }
}

package jp.mydns.kokoichi0206.countdowntimer.datamanager {
    class DataStoreManager
}

' Contract内にInterfaceを定義
MainContract +- View
MainContract +- Router
MainContract +- Interactor
MainContract +- InteractorCallback
MainContract +- Presenter

' VIPERコンポーネントの依存関係
Presenter <--- MainActivity
InteractorCallback <--- MainInteractor
View <--- MainPresenter
Router <--- MainPresenter
Interactor <--- MainPresenter

' モジュール組み立て
MainActivity <.... MainAssembler
MainRouter <.... MainAssembler
MainInteractor <.... MainAssembler
MainPresenter <.... MainAssembler

MainInteractor -> DataStoreManager
@enduml
```