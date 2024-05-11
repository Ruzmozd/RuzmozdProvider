package ir.hirkancorp.data.profile_image.remote

import io.ktor.client.HttpClient
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.headers
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import ir.hirkancorp.data.preferences.repository.LocalServiceRepository

class ProfileImageClient(
    private val httpClient: HttpClient,
    private val localServiceRepository: LocalServiceRepository
) {

    suspend fun uploadProfileImage(imageByteArray: ByteArray) = httpClient.submitFormWithBinaryData(
        url = "upload_profile_image",
        formData = formData {
            append("image", imageByteArray, Headers.build {
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
}