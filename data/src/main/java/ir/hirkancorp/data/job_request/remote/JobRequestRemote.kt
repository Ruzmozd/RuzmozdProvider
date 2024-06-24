package ir.hirkancorp.data.job_request.remote

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse

class JobRequestRemote(private val httpClient: HttpClient) {

    suspend fun acceptJobrequest(requestId: Int): HttpResponse = httpClient.get {
        url("accept_request")
        parameter("request_id", requestId)
    }

    suspend fun cancelJobRequest(requestId: Int): HttpResponse = httpClient.get {
        url("cancel_job_request")
        parameter("request_id", requestId)
    }

}