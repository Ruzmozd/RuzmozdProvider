package ir.hirkancorp.domain.provider_location.repository

import ir.hirkancorp.domain.utils.ApiResult
import kotlinx.coroutines.flow.Flow

interface ProviderLocationRepository {

    suspend fun updateProviderLocation(lat: Double, lng: Double): Flow<ApiResult<String>>

}