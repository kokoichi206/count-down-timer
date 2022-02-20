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

![](imgs/class_main.png)

## シーケンス図

``` mermaid
%%{init: {'theme': 'forest' } }%%
sequenceDiagram
    actor User
    participant MainActivity as <<View:UI制御>><br />MainActivity
    participant MainPresenter as <<Presenter:ビジネスロジック>><br />MainPresenter
    participant MainInteractor as <<Interactor:機能>><br />MainInteractor
    participant MainRouter as <<Router:画面遷移>><br />MainRouter

    note over User,MainRouter: 画面生成
    User ->> MainActivity : アプリ起動。
    MainActivity ->> MainPresenter : onCreate()
    MainPresenter ->> MainInteractor : readInitialSettings()
    note right of MainInteractor: DataStoreManager#readString()
    alt 前データが存在する
    MainInteractor ->> MainPresenter : onReadInitialSettingsCompleted()
    else 前データが存在しない
    MainInteractor ->> MainPresenter : onReadInitialSettingsFailed()
    end

    note over User,MainRouter: メニュー表示
    User ->> MainActivity : 縦三点ボタンをクリック。
    MainActivity ->> User : メニューを表示させる。
    opt ライセンス情報
    User ->> MainActivity : ライセンスメニューをクリック。
    MainActivity ->> MainPresenter : onLicenseClicked()
    MainPresenter ->> MainRouter : launchLicenseActivity()
    MainRouter ->> User : ライセンス画面を表示させる。
    end
    opt プライバシーポリシー
    User ->> MainActivity : プライバシーポリシーメニューをクリック。
    MainActivity ->> MainPresenter : onPrivacyPolicyClicked()
    MainPresenter ->> MainRouter : launchPrivacyPolicyActivity()
    MainRouter ->> User : プライバシーポリシー画面を表示させる。
    end
    User ->> MainActivity : メニューの外側をクリック。
    MainActivity ->> User : メニューを閉じる。
```

