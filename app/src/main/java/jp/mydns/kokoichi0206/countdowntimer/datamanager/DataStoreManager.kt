package jp.mydns.kokoichi0206.countdowntimer.datamanager

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

val Context.settingDataStore: DataStore<Preferences> by preferencesDataStore(name = "timer")

/**
 * Data storageのManager。
 */
open class DataStoreManager {
    /**
     * Key sets for Data store.
     */
    companion object {
        const val KEY_TIMER_TITLE = "title"
        const val KEY_STARTED_AT = "started_at"
        const val KEY_DEADLINE = "deadline"
    }


    open suspend fun writeBoolean(context: Context, key: String, value: Boolean) =
        withContext(Dispatchers.IO) {

            val wrappedKey = booleanPreferencesKey(key)
            context.settingDataStore.edit {
                it[wrappedKey] = value
            }
        }

    open suspend fun readBoolean(context: Context, key: String, default: Boolean = false): Boolean =
        withContext(Dispatchers.IO) {

            val wrappedKey = booleanPreferencesKey(key)
            val valueFlow: Flow<Boolean> = context.settingDataStore.data.map {
                it[wrappedKey] ?: default
            }
            return@withContext valueFlow.first()
        }

    open suspend fun writeString(context: Context, key: String, value: String) =
        withContext(Dispatchers.IO) {

            val wrappedKey = stringPreferencesKey(key)
            context.settingDataStore.edit {
                it[wrappedKey] = value
            }
        }

    open suspend fun readString(context: Context, key: String, default: String = ""): String =
        withContext(Dispatchers.IO) {

            val wrappedKey = stringPreferencesKey(key)
            val valueFlow: Flow<String> = context.settingDataStore.data.map {
                it[wrappedKey] ?: default
            }
            return@withContext valueFlow.first()
        }
}
