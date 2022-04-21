package jp.mydns.kokoichi0206.viper

/**
 * VIPER アーキテクチャのInteractorコンポーネント。
 */
interface Interactor : Node {
    /**
     * 実行結果を受け取るCallbackを指定する。
     *
     * @param callback [InteractorCallback]
     */
    fun setInteractorCallback(callback: InteractorCallback)
}
