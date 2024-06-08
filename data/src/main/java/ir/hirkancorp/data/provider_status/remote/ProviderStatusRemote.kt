package ir.hirkancorp.data.provider_status.remote

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse

class ProviderStatusRemote(private val httpClient: HttpClient) {

    suspend fun updateStatus(status: Int): HttpResponse = httpClient.get {
        url("update_status")
        parameter("status", status)
    }

}