package ir.hirkancorp.domain.profile_image.use_cases

import ir.hirkancorp.domain.profile_image.repository.ProfileImageRepository
import ir.hirkancorp.domain.utils.ApiResult
import kotlinx.coroutines.flow.Flow

class ProfileImageUseCase(private val profileImageRepository: ProfileImageRepository) {

    suspend operator fun invoke(imageByteArray: ByteArray) : Flow<ApiResult<String>> = profileImageRepository.uploadProfileImage(imageByteArray)

}