package ir.hirkancorp.data.provider_location.remote

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse

class ProviderLocationRemote(private val httpClient: HttpClient) {

    suspend fun updateProviderLocation(lat: Double, lng: Double): HttpResponse = httpClient.get {
        url("update_provider_location")
        parameter("latitude", lat)
        parameter("longitude", lng)
    }

}