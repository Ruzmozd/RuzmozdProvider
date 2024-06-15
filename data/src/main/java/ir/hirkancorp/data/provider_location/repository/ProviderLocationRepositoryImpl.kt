package ir.hirkancorp.data.provider_location.repository

import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode
import io.ktor.http.HttpStatusCode.Companion.Forbidden
import io.ktor.http.HttpStatusCode.Companion.UnprocessableEntity
import ir.hirkancorp.data.common.api_utils.commonRequest
import ir.hirkancorp.data.common.model.EmptyDataHttpResponseModel
import ir.hirkancorp.data.provider_location.remote.ProviderLocationRemote
import ir.hirkancorp.domain.provider_location.repository.ProviderLocationRepository
import ir.hirkancorp.domain.utils.ApiResult
import kotlinx.coroutines.flow.Flow

class ProviderLocationRepositoryImpl(private val client: ProviderLocationRemote): ProviderLocationRepository {
    override suspend fun updateProviderLocation(lat: Double, lng: Double): Flow<ApiResult<String>> = commonRequest(
        httpResponse = { client.updateProviderLocation(lat = lat, lng = lng) },
        errorCodes = listOf(Forbidden, UnprocessableEntity),
        successAction = Pair(HttpStatusCode.OK) { response ->
            response.body<EmptyDataHttpResponseModel>().statusMessage
        }

    )
}