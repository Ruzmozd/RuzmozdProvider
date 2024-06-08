package ir.hirkancorp.data.provider_status.repository

import io.ktor.http.HttpStatusCode
import ir.hirkancorp.data.common.api_utils.commonRequest
import ir.hirkancorp.data.provider_status.remote.ProviderStatusRemote
import ir.hirkancorp.domain.provider_status.repository.ProviderStatusRepository
import ir.hirkancorp.domain.utils.ApiResult
import kotlinx.coroutines.flow.Flow

class ProviderStatusRepositoryImpl(private val client: ProviderStatusRemote): ProviderStatusRepository {
    override suspend fun updateStatus(status: Int): Flow<ApiResult<Boolean>> = commonRequest(
        httpResponse = { client.updateStatus(status = status) },
        errorCodes = listOf(),
        successAction = Pair(HttpStatusCode.OK) { _ -> true }

    )
}