package ir.hirkancorp.data.upload_document.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UploadDocumentData(
    @SerialName("document_url")
    val documentURL: String? = null,
    @SerialName("document_status")
    val documentStatus: Int? = null
)