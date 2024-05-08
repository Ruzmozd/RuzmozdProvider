package ir.hirkancorp.data.preferences.repository

interface LocalServiceRepository {

    suspend fun saveAccessToken(accessToken: String)

    suspend fun clearAppData()

    suspend fun getAccessToken(): String?

}