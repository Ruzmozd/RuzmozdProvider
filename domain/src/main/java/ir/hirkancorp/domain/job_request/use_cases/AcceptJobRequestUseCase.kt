package ir.hirkancorp.domain.job_request.use_cases

import ir.hirkancorp.domain.job_request.model.JobProgress
import ir.hirkancorp.domain.job_request.repository.JobRequestRepository
import ir.hirkancorp.domain.utils.ApiResult
import kotlinx.coroutines.flow.Flow

class AcceptJobRequestUseCase(private val jobRequestRepository: JobRequestRepository) {

    suspend operator fun invoke(requestId: Int): Flow<ApiResult<JobProgress>> = jobRequestRepository.acceptJobRequest(requestId = requestId)

}