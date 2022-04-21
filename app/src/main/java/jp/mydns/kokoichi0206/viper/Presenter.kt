package jp.mydns.kokoichi0206.viper

/**
 * VIPER アーキテクチャのPresenterコンポーネント。
 */
interface Presenter {
    /**
     * 依存オブジェクトの結合を解除する。
     */
    fun disassembleModules()
}
