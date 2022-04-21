package jp.mydns.kokoichi0206.viper

import android.content.Context

/**
 * VIPER アーキテクチャの起点。
 */
interface Trigger {
    /**
     * VIPERコンポーネントの結合を開始する。
     *
     * @return 戻り値のPresenterクラスを呼び出し元のメンバ変数として保持する。
     */
    fun beginAssembleModules(context: Context): Presenter

    /**
     * VIPERコンポーネントの結合の解除を開始する。
     */
    fun beginDisassembleModules()
}
