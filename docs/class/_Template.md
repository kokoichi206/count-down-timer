# テンプレート

## 概要
＜HOGE＞の関連クラス

## クラス一覧
- [TemplateContract](https://github.com/kokoichi206/count-down-timer/tree/main/app/src/main/java/jp/mydns/kokoichi206/countdowntimer/module/template/contract/TemplateContract.kt)
    - コンポーネント間で共有される定数、Interfaceを定義するContractクラス
- [TemplateActivity](https://github.com/kokoichi206/count-down-timer/tree/main/app/src/main/java/jp/mydns/kokoichi206/countdowntimer/module/template/view/TemplateActivity.kt)
    - UI制御を担うVIewクラス
- [TemplateInteractor](https://github.com/kokoichi206/count-down-timer/tree/main/app/src/main/java/jp/mydns/kokoichi206/countdowntimer/module/template/interactor/TemplateInteractor.kt)
    - 機能を担うInteractorクラス
- [TemplateRouter](https://github.com/kokoichi206/count-down-timer/tree/main/app/src/main/java/jp/mydns/kokoichi206/countdowntimer/module/template/router/TemplateRouter.kt)
    - 画面遷移を担うRouterクラス
- [TemplatePresenter](https://github.com/kokoichi206/count-down-timer/tree/main/app/src/main/java/jp/mydns/kokoichi206/countdowntimer/module/template/presenter/TemplatePresenter.kt)
    - ビジネスロジックを担うPresenterクラス
- [TemplateAssembler](https://github.com/kokoichi206/count-down-timer/tree/main/app/src/main/java/jp/mydns/kokoichi206/countdowntimer/module/template/assembler/TemplateAssembler.kt)
    - モジュールの組み立てを担うAssemblerクラス

## クラス図

``` plantuml
@startuml
/'
メソッドなどの各メンバーの記載は省略する
'/
namespace jp.mydns.kokoichi206.viper #CCCCCC {
    interface View
    interface Router
    interface Interactor
    interface InteractorCallback
    interface Presenter
    interface Assembler
}

package jp.mydns.kokoichi206.countdowntimer.module.template {
    package contract {
        class TemplateContract
        interface View extends jp.mydns.kokoichi206.viper.View
        interface Router extends jp.mydns.kokoichi206.viper.Router
        interface Interactor extends jp.mydns.kokoichi206.viper.Interactor
        interface InteractorCallback extends jp.mydns.kokoichi206.viper.InteractorCallback
        interface Presenter extends jp.mydns.kokoichi206.viper.Presenter
    }

    package view {
        class TemplateActivity #88FF88 implements View
    }

    package router {
        class TemplateRouter #88FF88 implements Router
    }

    package interactor {
        class TemplateInteractor #88FF88 implements Interactor
    }

    package presenter {
        class TemplatePresenter #88FF88 implements Presenter
    }

    package assembler {
        class TemplateAssembler #88FF88 implements jp.mydns.kokoichi206.viper.Assembler
    }
}

' Contract内にInterfaceを定義
TemplateContract +- View
TemplateContract +- Router
TemplateContract +- Interactor
TemplateContract +- InteractorCallback
TemplateContract +- Presenter

' VIPERコンポーネントの依存関係
Presenter <--- TemplateActivity
InteractorCallback <--- TemplateInteractor
View <--- TemplatePresenter
Router <--- TemplatePresenter
Interactor <--- TemplatePresenter

' モジュール組み立て
TemplateActivity <.... TemplateAssembler
TemplateRouter <.... TemplateAssembler
TemplateInteractor <.... TemplateAssembler
TemplatePresenter <.... TemplateAssembler
@enduml
```

## シーケンス図

``` plantuml
@startuml

actor User
participant TemplateActivity <<View:UI制御>>
participant TemplatePresenter <<Presenter:ビジネスロジック>>
participant TemplateInteractor <<Interactor:機能>>
participant TemplateRouter <<Router:画面遷移>>

User -> TemplateActivity : 操作。
TemplateActivity -> TemplatePresenter : TemplatePresenterのメソッド()

@enduml
```
