package ir.hirkancorp.domain.logout.repository

interface LogOutRepository {

    suspend fun clearAppDataAndLogout()

}