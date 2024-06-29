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

    suspend fun arriveNow(jobId: Int): HttpResponse = httpClient.get {
        url("arrive_now")
        parameter("job_id", jobId)
    }

    suspend fun beginJob(jobId: Int): HttpResponse = httpClient.get {
        url("begin_job")
        parameter("job_id", jobId)
    }

    suspend fun endJob(jobId: Int): HttpResponse = httpClient.get {
        url("end_job")
        parameter("job_id", jobId)
    }

    suspend fun cancelJob(jobId: Int, reasonId: Int, cancelComments: String): HttpResponse = httpClient.get {
        url("cancel_job")
        parameter("job_id", jobId)
        parameter("reason_id", reasonId)
        parameter("cancel_comments", cancelComments)
    }

    suspend fun cancelReasons(): HttpResponse = httpClient.get {
        url("cancel_reasons")
    }

}