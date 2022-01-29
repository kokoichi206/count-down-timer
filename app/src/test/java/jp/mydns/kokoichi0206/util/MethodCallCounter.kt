package jp.mydns.kokoichi0206.util

/**
 * メソッド呼び出し回数。
 */
class MethodCallCounter(
    private var map: MutableMap<String, Int> = HashMap()
) {
    /**
     * キーを初期化する。
     *
     * @param key キー。
     */
    private fun initKey(key: String) {
        if (!map.containsKey(key)) {
            map[key] = 0
        }
    }

    /**
     * カウンターを増加させる。
     *
     * @param key キー。
     */
    fun increment(key: String) {
        initKey(key)
        map[key] = map[key]!!.plus(1)
    }

    /**
     * 呼び出し回数を取得する。
     *
     * @param key キー。
     * @return 呼び出し回数。
     */
    fun getCount(key: String): Int {
        initKey(key)
        return map[key]!!
    }

    /**
     * 全削除。
     */
    fun clear() {
        map.clear()
    }
}
