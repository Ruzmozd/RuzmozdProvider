package ir.hirkancorp.data.job_progress.remote

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse

class JobProgressClient(private val httpClient: HttpClient) {

    suspend fun getJobProgress(jobId: Int): HttpResponse = httpClient.get {
        url("get_job_progress")
        parameter("job_id", jobId)
    }

}