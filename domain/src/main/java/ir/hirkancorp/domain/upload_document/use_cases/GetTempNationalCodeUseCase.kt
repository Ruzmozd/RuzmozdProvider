package ir.hirkancorp.domain.upload_document.use_cases

import ir.hirkancorp.domain.upload_document.repository.UploadDocumentRepository
import kotlinx.coroutines.flow.first

class GetTempNationalCodeUseCase(private val uploadDocumentRepository: UploadDocumentRepository) {
    suspend operator fun invoke() : String = uploadDocumentRepository.getTempNationalCode().first()
}