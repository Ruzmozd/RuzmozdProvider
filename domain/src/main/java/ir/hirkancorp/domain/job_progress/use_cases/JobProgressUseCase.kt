package ir.hirkancorp.domain.job_progress.use_cases

import ir.hirkancorp.domain.job_progress.repository.JobProgressRepository
import ir.hirkancorp.domain.utils.ApiResult
import ir.hirkancorp.domain.job_progress.model.JobProgress
import kotlinx.coroutines.flow.Flow

class JobProgressUseCase(private val jobProgressRepository: JobProgressRepository) {

    suspend operator fun invoke(jobId: Int): Flow<ApiResult<JobProgress>> = jobProgressRepository.getJobProgress(jobId)

}