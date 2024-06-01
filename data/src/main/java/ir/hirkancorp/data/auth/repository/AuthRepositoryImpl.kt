package ir.hirkancorp.data.auth.repository

import ir.hirkancorp.data.preferences.repository.LocalServiceRepository
import ir.hirkancorp.domain.auth.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthRepositoryImpl(private val localServiceRepository: LocalServiceRepository):
    AuthRepository {

    override suspend fun isAuthenticate(): Flow<Boolean> = flow {
        localServiceRepository.getAccessToken().let { token ->
            when {
                token.isNullOrBlank() -> emit(false)
                else -> emit(true)
            }
        }
    }

}