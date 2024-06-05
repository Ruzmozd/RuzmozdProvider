package ir.hirkancorp.data.provider_profile.remote

import io.ktor.client.HttpClient
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import ir.hirkancorp.data.preferences.repository.LocalServiceRepository

class ProviderProfileRemote(
    private val httpClient: HttpClient,
    private val localServiceRepository: LocalServiceRepository
) {
    suspend fun providerProfile(
        firstName: String?,
        lastName: String?,
        city: Int?,
        image: ByteArray?
    ) = image?.let {
        httpClient.submitFormWithBinaryData(
            url = "get_provider_profile",
            formData = formData {
                firstName?.let { append("first_name", it) }
                lastName?.let { append("last_name", it) }
                city?.let { append("city", it) }
                append("profile_image", image, Headers.build {
                    append(HttpHeaders.ContentType, "image/jpg")
                    append(HttpHeaders.ContentDisposition, "filename=profileimage.jpg")
                })
            }
        ) {
            val accessToken = localServiceRepository.getAccessToken()
            headers {
                append(HttpHeaders.Authorization, "Bearer $accessToken")
            }
        }
    } ?: run {
        httpClient.post {
            url("get_provider_profile")
            firstName?.let { parameter("first_name", it) }
            lastName?.let { parameter("last_name", it) }
            city?.let { parameter("city", it) }
        }
    }
}