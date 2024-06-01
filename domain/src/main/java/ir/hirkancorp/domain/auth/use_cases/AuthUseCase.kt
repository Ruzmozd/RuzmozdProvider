package ir.hirkancorp.domain.auth.use_cases

import ir.hirkancorp.domain.auth.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

class AuthUseCase(private val repository: AuthRepository) {

    suspend operator fun invoke(): Flow<Boolean> = repository.isAuthenticate()

}