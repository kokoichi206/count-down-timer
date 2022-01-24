package jp.mydns.kokoichi0206.viper

/**
 * VIPER アーキテクチャの終点。
 */
interface Node {
    /**
     * 依存オブジェクトを取り除く。
     */
    fun onDisassemble()
}