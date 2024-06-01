package ir.hirkancorp.domain.auth.repository

import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun isAuthenticate(): Flow<Boolean>
    
}