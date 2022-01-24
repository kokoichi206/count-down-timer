package jp.mydns.kokoichi0206.viper

import android.content.Context

/**
 * VIPER アーキテクチャにおいて依存性を注入する。
 */
interface Assembler {
    /**
     * 依存オブジェクトを注入する。
     *
     * @param context コンテキスト。
     * @return 結合完了後のPresenter。
     */
    fun assembleModules(context: Context): Presenter
}