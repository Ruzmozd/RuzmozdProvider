package ir.hirkancorp.data.provider_profile.repository

import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode.Companion.Forbidden
import io.ktor.http.HttpStatusCode.Companion.NotFound
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.http.HttpStatusCode.Companion.Unauthorized
import ir.hirkancorp.data.common.api_utils.commonRequest
import ir.hirkancorp.data.common.model.HttpResponseModel
import ir.hirkancorp.data.provider_profile.mapper.toDomain
import ir.hirkancorp.data.provider_profile.remote.ProviderProfileRemote
import ir.hirkancorp.domain.provider_profile.models.ProviderProfile
import ir.hirkancorp.domain.provider_profile.repository.ProviderProfileRepository
import ir.hirkancorp.domain.utils.ApiResult
import kotlinx.coroutines.flow.Flow
import ir.hirkancorp.data.provider_profile.models.ProviderProfile as ProviderProfileData

class ProviderProfileRepositoryImpl(private val providerProfileRemote: ProviderProfileRemote): ProviderProfileRepository {

    override suspend fun providerProfile(
        firstName: String?,
        lastName: String?,
        city: Int?,
        profileImage: ByteArray?
    ): Flow<ApiResult<ProviderProfile>> = commonRequest(
        httpResponse = {
            providerProfileRemote.providerProfile(
                firstName = firstName,
                lastName = lastName,
                city = city,
                image = profileImage
            )
        },
        errorCodes = listOf(Unauthorized, Forbidden, NotFound),
        successAction = Pair(OK) { response ->
            response.body<HttpResponseModel<ProviderProfileData>>().data.providerProfile.toDomain()
        }

    )
}