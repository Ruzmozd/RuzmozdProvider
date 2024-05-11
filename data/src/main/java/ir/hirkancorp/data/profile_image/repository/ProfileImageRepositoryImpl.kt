package ir.hirkancorp.data.profile_image.repository

import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode.Companion.Conflict
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.http.HttpStatusCode.Companion.UnprocessableEntity
import ir.hirkancorp.data.common.api_utils.commonRequest
import ir.hirkancorp.data.common.model.HttpResponseModel
import ir.hirkancorp.data.profile_image.model.ImageUrlData
import ir.hirkancorp.data.profile_image.remote.ProfileImageClient
import ir.hirkancorp.domain.profile_image.repository.ProfileImageRepository
import ir.hirkancorp.domain.utils.ApiResult
import kotlinx.coroutines.flow.Flow

class ProfileImageRepositoryImpl(
    private val client: ProfileImageClient
) : ProfileImageRepository {

    override suspend fun uploadProfileImage(imageByteArray: ByteArray): Flow<ApiResult<String>> =
        commonRequest(
            httpResponse = { client.uploadProfileImage(imageByteArray) },
            errorCodes = listOf(Conflict, UnprocessableEntity),
            successAction = Pair(OK) { result ->
                result.body<HttpResponseModel<ImageUrlData>>().data.imageUrl ?: ""
            }
        )
}