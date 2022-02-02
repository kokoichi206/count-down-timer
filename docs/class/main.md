# メイン画面

## 概要
メイン画面の関連クラス

## クラス一覧
- [MainContract](https://github.com/kokoichi206/count-down-timer/tree/main/app/src/main/java/jp/mydns/kokoichi0206/countdowntimer/module/main/contract/MainContract.kt)
    - コンポーネント間で共有される定数、Interfaceを定義するContractクラス。
- [MainActivity](https://github.com/kokoichi206/count-down-timer/tree/main/app/src/main/java/jp/mydns/kokoichi0206/countdowntimer/module/main/view/MainActivity.kt)
    - UI制御を担うVIewクラス。
    - [HomeViwe](https://github.com/kokoichi206/count-down-timer/tree/main/app/src/main/java/jp/mydns/kokoichi0206/countdowntimer/module/main/view/HomeView.kt)
        - メイン画面を表示する。
- [MainInteractor](https://github.com/kokoichi206/count-down-timer/tree/main/app/src/main/java/jp/mydns/kokoichi0206/countdowntimer/module/main/interactor/MainInteractor.kt)
    - 機能を担うInteractorクラス。
    - [DataStoreManager](https://github.com/kokoichi206/count-down-timer/tree/main/app/src/main/java/jp/mydns/kokoichi0206/countdowntimer/datamanager/DataStoreManager.kt)
        - タイマーの情報を読み書きする。
            - タイトル
            - 開始時間
            - 終了予定時刻
- [MainRouter](https://github.com/kokoichi206/count-down-timer/tree/main/app/src/main/java/jp/mydns/kokoichi0206/countdowntimer/module/main/router/MainRouter.kt)
    - 画面遷移を担うRouterクラス。
    - ライセンス情報画面へ遷移する。
    - ユーザーデータ画面へ遷移する。
- [MainPresenter](https://github.com/kokoichi206/count-down-timer/tree/main/app/src/main/java/jp/mydns/kokoichi0206/countdowntimer/module/main/presenter/MainPresenter.kt)
    - ビジネスロジックを担うPresenterクラス。
- [MainAssembler](https://github.com/kokoichi206/count-down-timer/tree/main/app/src/main/java/jp/mydns/kokoichi0206/countdowntimer/module/main/assembler/MainAssembler.kt)
    - モジュールの組み立てを担うAssemblerクラス。

## クラス図

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

## シーケンス図

``` plantuml
@startuml

actor User
participant MainActivity <<View:UI制御>>
participant MainPresenter <<Presenter:ビジネスロジック>>
participant MainInteractor <<Interactor:機能>>
participant MainRouter <<Router:画面遷移>>

==画面生成==
User -> MainActivity : アプリ起動。
MainActivity -> MainPresenter : onCreate()
MainPresenter -> MainInteractor : readInitialSettings()
note right : DataStoreManager#readString()
alt 前データが存在する
MainPresenter <-- MainInteractor : onReadInitialSettingsCompleted()
MainActivity <- MainPresenter : setMainViewWithTime()
User <- MainActivity : 前回のデータを引き継いだ\nホーム画面を表示する。
else 前データが存在しない
MainPresenter <-- MainInteractor : onReadInitialSettingsFailed()
MainActivity <- MainPresenter : setMainView()
User <- MainActivity : ホーム画面を表示する。
end

==メニュー表示==
User -> MainActivity : 縦三点ボタンをクリック。
User <- MainActivity : メニューを表示させる。
opt ライセンス情報
User -> MainActivity : ライセンスメニューをクリック。
MainActivity -> MainPresenter : onLicenseClicked()
MainPresenter -> MainRouter : launchLicenseActivity()
User <- MainRouter : ライセンス画面を表示させる。
end
opt プライバシーポリシー
User -> MainActivity : プライバシーポリシーメニューをクリック。
MainActivity -> MainPresenter : onPrivacyPolicyClicked()
MainPresenter -> MainRouter : launchPrivacyPolicyActivity()
User <- MainRouter : プライバシーポリシー画面を表示させる。
end
User -> MainActivity : メニューの外側をクリック。
User <- MainActivity : メニューを閉じる。
@enduml
```
