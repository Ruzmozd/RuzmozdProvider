package ir.hirkancorp.data.upload_document.remote

import io.ktor.client.HttpClient
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.headers
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import ir.hirkancorp.data.preferences.repository.LocalServiceRepository

class UploadDocumentDataClient(
    private val httpClient: HttpClient,
    private val localServiceRepository: LocalServiceRepository
) {
    suspend fun uploadDocumentImage(imageByteArray: ByteArray,) = httpClient.submitFormWithBinaryData(
        url = "update_document",
        formData = formData {
            append("document_id", "1")
            append("document_image", imageByteArray, Headers.build {
                append(HttpHeaders.ContentType, "image/jpg")
                append(HttpHeaders.ContentDisposition, "filename=document_image.jpg")
            })
        },
    ) {
        val accessToken = localServiceRepository.getAccessToken()
        headers {
            append(HttpHeaders.Authorization, "Bearer $accessToken")
        }
    }
}