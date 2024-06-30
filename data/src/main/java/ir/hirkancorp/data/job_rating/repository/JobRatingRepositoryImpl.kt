package ir.hirkancorp.data.job_rating.repository

import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode.Companion.Conflict
import io.ktor.http.HttpStatusCode.Companion.Forbidden
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.http.HttpStatusCode.Companion.Unauthorized
import io.ktor.http.HttpStatusCode.Companion.UnprocessableEntity
import ir.hirkancorp.data.common.api_utils.commonRequest
import ir.hirkancorp.data.common.model.EmptyDataHttpResponseModel
import ir.hirkancorp.data.job_rating.remote.JobRatingClient
import ir.hirkancorp.domain.job_rating.repository.JobRatingRepository
import ir.hirkancorp.domain.utils.ApiResult
import kotlinx.coroutines.flow.Flow

class JobRatingRepositoryImpl(private val client: JobRatingClient) : JobRatingRepository {

    override suspend fun jobRating(
        jobId: Int,
        rating: Int,
        comment: String?
    ): Flow<ApiResult<String>> = commonRequest(
        httpResponse = { client.jobRating(jobId = jobId, rating = rating, comment = comment) },
        errorCodes = listOf(Unauthorized, Forbidden, Conflict, UnprocessableEntity),
        successAction = Pair(OK) { response ->
            response.body<EmptyDataHttpResponseModel>().statusMessage
        }

    )
}