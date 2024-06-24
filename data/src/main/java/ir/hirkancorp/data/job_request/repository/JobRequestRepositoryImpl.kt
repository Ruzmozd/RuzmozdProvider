package ir.hirkancorp.data.job_request.repository

import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode.Companion.Conflict
import io.ktor.http.HttpStatusCode.Companion.NotFound
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.http.HttpStatusCode.Companion.Unauthorized
import io.ktor.http.HttpStatusCode.Companion.UnprocessableEntity
import ir.hirkancorp.data.common.api_utils.commonRequest
import ir.hirkancorp.data.common.model.EmptyDataHttpResponseModel
import ir.hirkancorp.data.common.model.HttpResponseModel
import ir.hirkancorp.data.job_request.model.AcceptJobDto
import ir.hirkancorp.data.job_request.remote.JobRequestRemote
import ir.hirkancorp.domain.job_request.repository.JobRequestRepository
import ir.hirkancorp.domain.utils.ApiResult
import kotlinx.coroutines.flow.Flow

class JobRequestRepositoryImpl(private val jobRequestRemote: JobRequestRemote): JobRequestRepository {

    override suspend fun acceptJobRequest(requestId: Int): Flow<ApiResult<Int>> = commonRequest(
        httpResponse = { jobRequestRemote.acceptJobrequest(requestId = requestId) },
        errorCodes = listOf(Unauthorized, NotFound, Conflict, UnprocessableEntity),
        successAction = Pair(OK) { response ->
            response.body<HttpResponseModel<AcceptJobDto>>().data.jobId ?: 0
        }
    )

    override suspend fun cancelJobRequest(requestId: Int): Flow<ApiResult<String>> = commonRequest(
        httpResponse = { jobRequestRemote.cancelJobRequest(requestId = requestId) },
        errorCodes = listOf(Unauthorized, NotFound, Conflict, UnprocessableEntity),
        successAction = Pair(OK) { response ->
            response.body<EmptyDataHttpResponseModel>().statusMessage
        }
    )


}