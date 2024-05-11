package ir.hirkancorp.domain.upload_document.use_cases

import ir.hirkancorp.domain.upload_document.model.UploadDocument
import ir.hirkancorp.domain.upload_document.repository.UploadDocumentRepository
import ir.hirkancorp.domain.utils.ApiResult
import kotlinx.coroutines.flow.Flow

class UploadDocumentUseCase(private val uploadDocumentRepository: UploadDocumentRepository) {

    suspend operator fun invoke(imageByteArray: ByteArray) : Flow<ApiResult<UploadDocument>> = uploadDocumentRepository.uploadDocumentImage(imageByteArray)

}