package ir.hirkancorp.domain.job_rating.repository

import ir.hirkancorp.domain.utils.ApiResult
import kotlinx.coroutines.flow.Flow

interface JobRatingRepository {

    suspend fun jobRating(
        jobId: Int,
        rating: Int,
        comment: String?
    ): Flow<ApiResult<String>>

}