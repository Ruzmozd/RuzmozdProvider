package ir.hirkancorp.domain.provider_location.use_cases

import ir.hirkancorp.domain.provider_location.repository.ProviderLocationRepository
import ir.hirkancorp.domain.utils.ApiResult
import kotlinx.coroutines.flow.Flow

class ProviderLocationUseCase(private val providerLocationRepository: ProviderLocationRepository) {

    suspend operator fun invoke(lat: Double, lng: Double): Flow<ApiResult<String>>  {
        return providerLocationRepository.updateProviderLocation(lat = lat, lng = lng)
    }

}