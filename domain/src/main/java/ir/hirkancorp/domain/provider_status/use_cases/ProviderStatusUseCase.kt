package ir.hirkancorp.domain.provider_status.use_cases

import ir.hirkancorp.domain.provider_status.repository.ProviderStatusRepository
import ir.hirkancorp.domain.utils.ApiResult
import kotlinx.coroutines.flow.Flow

class ProviderStatusUseCase(private val providerStatusRepository: ProviderStatusRepository) {

    suspend operator fun invoke(status: Boolean): Flow<ApiResult<Boolean>>  {
        val statusAsInt = if (status) 1 else 0
        return providerStatusRepository.updateStatus(status = statusAsInt)
    }

}