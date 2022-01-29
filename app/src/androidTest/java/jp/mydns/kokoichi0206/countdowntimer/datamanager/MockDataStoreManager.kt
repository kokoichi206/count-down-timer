package jp.mydns.kokoichi0206.countdowntimer.datamanager

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import jp.mydns.kokoichi0206.countdowntimer.util.MethodCallCounter

class MockDataStoreManager(
    private val counter: MethodCallCounter = MethodCallCounter()
) : DataStoreManager() {

    lateinit var booleanKey: String
    lateinit var stringKey: String
    lateinit var stringValue: String
    lateinit var stringValueDefault: String

    enum class MockedMethod {
        WRITE_BOOLEAN,
        READ_BOOLEAN,
        WRITE_STRING,
        READ_STRING,
    }

    override suspend fun writeBoolean(context: Context, key: String, value: Boolean): Preferences {
        counter.increment(MockedMethod.WRITE_BOOLEAN.name)
        return super.writeBoolean(context, key, value)
    }

    override suspend fun readBoolean(context: Context, key: String, default: Boolean): Boolean {
        counter.increment(MockedMethod.READ_BOOLEAN.name)
        return super.readBoolean(context, key, default)
    }

    override suspend fun writeString(context: Context, key: String, value: String): Preferences {
        counter.increment(MockedMethod.WRITE_STRING.name)
        stringKey = key
        stringValue = value
        return super.writeString(context, key, value)
    }

    override suspend fun readString(context: Context, key: String, default: String): String {
        counter.increment(MockedMethod.READ_STRING.name)
        stringKey = key
        stringValueDefault = default
        return stringValue
    }

    fun clear() {
        counter.clear()
    }

    fun getCount(method: MockedMethod): Int {
        return counter.getCount(method.name)
    }
}