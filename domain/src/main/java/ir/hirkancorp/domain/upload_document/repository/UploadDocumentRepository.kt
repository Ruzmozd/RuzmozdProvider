package ir.hirkancorp.domain.upload_document.repository

import ir.hirkancorp.domain.upload_document.model.UploadDocument
import ir.hirkancorp.domain.utils.ApiResult
import kotlinx.coroutines.flow.Flow

interface UploadDocumentRepository {

    suspend fun uploadDocumentImage(imageByteArray: ByteArray): Flow<ApiResult<UploadDocument>>

    suspend fun getTempNationalCode(): Flow<String>
}