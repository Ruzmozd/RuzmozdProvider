package ir.hirkancorp.domain.requests.use_case

import androidx.paging.PagingData
import ir.hirkancorp.domain.requests.model.Service
import ir.hirkancorp.domain.requests.repository.RequestsRepository
import kotlinx.coroutines.flow.Flow

class RequestsUseCase(private val requestsRepository: RequestsRepository) {

    operator fun invoke(status: String?): Flow<PagingData<Service>> = requestsRepository.getRequests()

}