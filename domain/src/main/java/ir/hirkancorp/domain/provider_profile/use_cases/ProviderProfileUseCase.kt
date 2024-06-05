package ir.hirkancorp.domain.provider_profile.use_cases

import ir.hirkancorp.domain.provider_profile.models.ProviderProfile
import ir.hirkancorp.domain.provider_profile.repository.ProviderProfileRepository
import ir.hirkancorp.domain.utils.ApiResult
import kotlinx.coroutines.flow.Flow

class ProviderProfileUseCase(private val providerProfileRepository: ProviderProfileRepository) {

    suspend operator fun invoke(
        firstName: String?,
        lastName: String?,
        city: Int?,
        profileImage: ByteArray?
    ): Flow<ApiResult<ProviderProfile>> = providerProfileRepository.providerProfile(
        firstName = firstName,
        lastName = lastName,
        city = city,
        profileImage = profileImage
    )

}