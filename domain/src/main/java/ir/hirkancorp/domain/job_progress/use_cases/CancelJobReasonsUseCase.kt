package ir.hirkancorp.domain.job_progress.use_cases

import ir.hirkancorp.domain.job_progress.model.CancelJobReasons
import ir.hirkancorp.domain.job_progress.repository.JobProgressRepository
import ir.hirkancorp.domain.utils.ApiResult
import kotlinx.coroutines.flow.Flow

class CancelJobReasonsUseCase(private val jobProgressRepository: JobProgressRepository) {

    suspend operator fun invoke(): Flow<ApiResult<CancelJobReasons>> = jobProgressRepository.cancelReasons()

}