package ir.hirkancorp.data.preferences.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import ir.hirkancorp.data.preferences.PreferencesKeys.ACCESS_TOKEN_KEY
import kotlinx.coroutines.flow.firstOrNull

class LocalServiceRepositoryImpl(
    private val dataStore: DataStore<Preferences>
): LocalServiceRepository {
    override suspend fun saveAccessToken(accessToken: String) {
        dataStore.edit {
            it[ACCESS_TOKEN_KEY] = accessToken
        }
    }

    override suspend fun clearAppData() {
        dataStore.edit {
            it.clear()
        }
    }

    override suspend fun getAccessToken() = dataStore.data.firstOrNull()?.get(ACCESS_TOKEN_KEY)

}