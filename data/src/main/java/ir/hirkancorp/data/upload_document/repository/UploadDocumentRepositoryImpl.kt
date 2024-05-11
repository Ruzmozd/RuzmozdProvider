package ir.hirkancorp.data.upload_document.repository

import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode.Companion.Forbidden
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.http.HttpStatusCode.Companion.Unauthorized
import io.ktor.http.HttpStatusCode.Companion.UnprocessableEntity
import ir.hirkancorp.data.common.api_utils.commonRequest
import ir.hirkancorp.data.common.model.HttpResponseModel
import ir.hirkancorp.data.common.static_data.TempDataRepository
import ir.hirkancorp.data.upload_document.mapper.toDomain
import ir.hirkancorp.data.upload_document.model.UploadDocumentData
import ir.hirkancorp.data.upload_document.remote.UploadDocumentDataClient
import ir.hirkancorp.domain.upload_document.model.UploadDocument
import ir.hirkancorp.domain.upload_document.repository.UploadDocumentRepository
import ir.hirkancorp.domain.utils.ApiResult
import ir.hirkancorp.domain.utils.Constants.TEMP_DATA_NATIONAL_CODE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UploadDocumentRepositoryImpl(
    private val client: UploadDocumentDataClient,
    private val tempDataRepository: TempDataRepository<String>
) : UploadDocumentRepository {

    override suspend fun uploadDocumentImage(imageByteArray: ByteArray): Flow<ApiResult<UploadDocument>> =
        commonRequest(
            httpResponse = { client.uploadDocumentImage(imageByteArray) },
            errorCodes = listOf( Forbidden, Unauthorized, UnprocessableEntity),
            successAction = Pair(OK) { result ->
                result.body<HttpResponseModel<UploadDocumentData>>().data.toDomain()
            }
        )

    override suspend fun getTempNationalCode(): Flow<String> {
        return tempDataRepository.getData(TEMP_DATA_NATIONAL_CODE)
            .map { if (it.isNullOrEmpty()) "" else it }
    }
}