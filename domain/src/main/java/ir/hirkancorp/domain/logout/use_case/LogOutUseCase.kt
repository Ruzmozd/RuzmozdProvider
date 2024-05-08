package ir.hirkancorp.domain.logout.use_case

import ir.hirkancorp.domain.logout.repository.LogOutRepository

class LogOutUseCase(private val logOutRepository: LogOutRepository) {

    suspend operator fun invoke() = logOutRepository.clearAppDataAndLogout()

}