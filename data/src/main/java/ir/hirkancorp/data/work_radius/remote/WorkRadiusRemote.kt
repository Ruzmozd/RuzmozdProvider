package ir.hirkancorp.data.work_radius.remote

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse

class WorkRadiusRemote(private val httpClient: HttpClient) {

    suspend fun updateWorkradius(radius: Int): HttpResponse = httpClient.get {
        url("update_work_radius")
        parameter("work_radius", radius)
    }

}