package ir.hirkancorp.domain.job_request.repository

import ir.hirkancorp.domain.job_request.model.JobProgress
import ir.hirkancorp.domain.utils.ApiResult
import kotlinx.coroutines.flow.Flow

interface JobRequestRepository {

    suspend fun acceptJobRequest(requestId: Int): Flow<ApiResult<JobProgress>>

    suspend fun cancelJobRequest(requestId: Int): Flow<ApiResult<String>>

}