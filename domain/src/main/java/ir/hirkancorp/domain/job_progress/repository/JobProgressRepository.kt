package ir.hirkancorp.domain.job_progress.repository

import ir.hirkancorp.domain.utils.ApiResult
import ir.hirkancorp.domain.job_progress.model.JobProgress
import kotlinx.coroutines.flow.Flow

interface JobProgressRepository {

    suspend fun getJobProgress(jobId: Int): Flow<ApiResult<JobProgress>>

}