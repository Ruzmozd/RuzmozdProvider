package ir.hirkancorp.domain.provider_status.repository

import ir.hirkancorp.domain.utils.ApiResult
import kotlinx.coroutines.flow.Flow

interface ProviderStatusRepository {

    suspend fun updateStatus(status: Int): Flow<ApiResult<Boolean>>

}