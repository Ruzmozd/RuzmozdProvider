package ir.hirkancorp.domain.profile_image.repository

import ir.hirkancorp.domain.utils.ApiResult
import kotlinx.coroutines.flow.Flow

interface ProfileImageRepository {

    suspend fun uploadProfileImage(imageByteArray: ByteArray): Flow<ApiResult<String>>

}