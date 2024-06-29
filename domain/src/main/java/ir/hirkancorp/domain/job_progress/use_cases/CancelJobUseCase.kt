package ir.hirkancorp.domain.job_progress.use_cases

import ir.hirkancorp.domain.job_progress.repository.JobProgressRepository
import ir.hirkancorp.domain.utils.ApiResult
import kotlinx.coroutines.flow.Flow

class CancelJobUseCase(private val jobProgressRepository: JobProgressRepository) {

    suspend operator fun invoke(
        jobId: Int,
        reasonId: Int,
        cancelComment: String,
    ): Flow<ApiResult<String>> = jobProgressRepository.cancelJob(
        jobId = jobId,
        reasonId = reasonId,
        cancelComment = cancelComment
    )

}