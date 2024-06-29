package ir.hirkancorp.domain.job_progress.repository

import ir.hirkancorp.domain.job_progress.model.CancelJobReasons
import ir.hirkancorp.domain.utils.ApiResult
import ir.hirkancorp.domain.job_progress.model.JobProgress
import kotlinx.coroutines.flow.Flow

interface JobProgressRepository {

    suspend fun getJobProgress(jobId: Int): Flow<ApiResult<JobProgress>>
    suspend fun arriveNow(jobId: Int): Flow<ApiResult<JobProgress>>
    suspend fun beginJob(jobId: Int): Flow<ApiResult<JobProgress>>
    suspend fun endJob(jobId: Int): Flow<ApiResult<JobProgress>>
    suspend fun cancelReasons(): Flow<ApiResult<CancelJobReasons>>
    suspend fun cancelJob(jobId: Int, reasonId: Int, cancelComment: String): Flow<ApiResult<String>>

}