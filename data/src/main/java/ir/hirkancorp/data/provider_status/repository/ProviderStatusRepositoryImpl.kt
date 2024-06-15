package ir.hirkancorp.data.provider_status.repository

import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode
import ir.hirkancorp.data.common.api_utils.commonRequest
import ir.hirkancorp.data.common.model.HttpResponseModel
import ir.hirkancorp.data.provider_status.model.ProviderStatus
import ir.hirkancorp.data.provider_location.remote.ProviderLocationRemote
import ir.hirkancorp.domain.provider_status.repository.ProviderStatusRepository
import ir.hirkancorp.domain.utils.ApiResult
import kotlinx.coroutines.flow.Flow

class ProviderStatusRepositoryImpl(private val client: ProviderLocationRemote): ProviderStatusRepository {
    override suspend fun updateStatus(status: Int): Flow<ApiResult<Boolean>> = commonRequest(
        httpResponse = { client.updateProviderLocation(status = status) },
        errorCodes = listOf(),
        successAction = Pair(HttpStatusCode.OK) { response ->
            response.body<HttpResponseModel<ProviderStatus>>().data.isOnline
        }

    )
}