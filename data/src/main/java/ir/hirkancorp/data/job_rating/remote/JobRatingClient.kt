package ir.hirkancorp.data.job_rating.remote

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse

class JobRatingClient(private val httpClient: HttpClient) {

    suspend fun jobRating(
        jobId: Int,
        rating: Int,
        comment: String?
    ): HttpResponse = httpClient.get {
        url("job_rating")
        parameter("job_id", jobId)
        parameter("rating", rating)
        comment?.let {  parameter("rating_comment", comment) }

    }

}