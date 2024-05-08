package ir.hirkancorp.data.logout.repository

import ir.hirkancorp.data.preferences.repository.LocalServiceRepository
import ir.hirkancorp.domain.logout.repository.LogOutRepository

class LogOutRepositoryImpl(private val localService: LocalServiceRepository): LogOutRepository {

    override suspend fun clearAppDataAndLogout() = localService.clearAppData()

}