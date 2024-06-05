package ir.hirkancorp.domain.provider_profile.repository

import ir.hirkancorp.domain.provider_profile.models.ProviderProfile
import ir.hirkancorp.domain.utils.ApiResult
import kotlinx.coroutines.flow.Flow

interface ProviderProfileRepository {

    suspend fun providerProfile(
        firstName: String?,
        lastName: String?,
        city: Int?,
        profileImage: ByteArray?
    ): Flow<ApiResult<ProviderProfile>>

}