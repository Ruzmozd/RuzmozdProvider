package ir.hirkancorp.domain.job_rating.use_cases

import ir.hirkancorp.domain.job_rating.repository.JobRatingRepository
import ir.hirkancorp.domain.utils.ApiResult
import kotlinx.coroutines.flow.Flow

class JobRatingUseCase(private val repository: JobRatingRepository) {

    suspend operator fun invoke(
        jobId: Int,
        rating: Int,
        comment: String?
    ): Flow<ApiResult<String>> = repository.jobRating(
        jobId = jobId,
        rating = rating,
        comment = comment
    )

}