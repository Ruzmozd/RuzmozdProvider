package ir.hirkancorp.domain.provider_status.use_cases

import ir.hirkancorp.domain.provider_status.repository.ProviderStatusRepository
import ir.hirkancorp.domain.utils.ApiResult
import kotlinx.coroutines.flow.Flow

class ProviderStatusUseCase(private val providerStatusRepository: ProviderStatusRepository) {

    suspend operator fun invoke(status: Int): Flow<ApiResult<Boolean>> = providerStatusRepository.updateStatus(status = status)

}