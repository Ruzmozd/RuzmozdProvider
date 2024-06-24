package ir.hirkancorp.data.job_progress.repository

import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode.Companion.NotFound
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.http.HttpStatusCode.Companion.Unauthorized
import io.ktor.http.HttpStatusCode.Companion.UnprocessableEntity
import ir.hirkancorp.data.common.api_utils.commonRequest
import ir.hirkancorp.data.common.model.HttpResponseModel
import ir.hirkancorp.data.job_progress.mapper.toDomain
import ir.hirkancorp.data.job_progress.model.JobProgressData
import ir.hirkancorp.data.job_progress.remote.JobProgressClient
import ir.hirkancorp.domain.job_progress.model.JobProgress
import ir.hirkancorp.domain.job_progress.repository.JobProgressRepository
import ir.hirkancorp.domain.utils.ApiResult
import kotlinx.coroutines.flow.Flow

class JobProgressRepositoryImpl(private val jobProgressClient: JobProgressClient) : JobProgressRepository {

    override suspend fun getJobProgress(jobId: Int): Flow<ApiResult<JobProgress>> = commonRequest(
        httpResponse = { jobProgressClient.getJobProgress(jobId) },
        errorCodes = listOf(Unauthorized, NotFound, UnprocessableEntity),
        successAction = Pair(OK) { response ->
            response.body<HttpResponseModel<JobProgressData>>().data.toDomain()
        })
}