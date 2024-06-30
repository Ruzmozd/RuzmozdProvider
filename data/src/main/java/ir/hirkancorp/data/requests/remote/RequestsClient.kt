package ir.hirkancorp.data.requests.remote

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse

class RequestsClient(private val httpClient: HttpClient) {

    suspend fun getRequests(
        paginate: Int,
        page: Int
    ): HttpResponse = httpClient.get {
        url("get_jobs_list")
        parameter("paginate", paginate)
        parameter("page", page)
    }

}