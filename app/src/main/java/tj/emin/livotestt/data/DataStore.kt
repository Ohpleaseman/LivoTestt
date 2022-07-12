package tj.emin.livotestt.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

private const val USER_DATA_NAME = "user_data"
const val NO_VALUE_STRING = "no value"

private object PreferencesKeys {
    val EMAIL = stringPreferencesKey("email")
}

val Context.dataStore by preferencesDataStore(
    name = USER_DATA_NAME
)

suspend fun setUserEmail(dataStore: DataStore<Preferences>, value: String) {
    dataStore.edit { settings ->
        settings[PreferencesKeys.EMAIL] = value
    }
}

suspend fun getUserEmail(dataStore: DataStore<Preferences>): String? {
    val preferences = dataStore.data.first()
    return preferences[PreferencesKeys.EMAIL]
}