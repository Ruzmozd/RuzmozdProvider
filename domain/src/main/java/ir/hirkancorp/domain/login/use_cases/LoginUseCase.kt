package ir.hirkancorp.domain.login.use_cases

import ir.hirkancorp.domain.login.model.User
import ir.hirkancorp.domain.login.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import ir.hirkancorp.domain.utils.ApiResult
import ir.hirkancorp.domain.login.model.Otp

class LoginUseCase(private val loginRepository: LoginRepository) {

    suspend operator fun invoke(phoneNumber: String): Flow<ApiResult<String>> {
       return loginRepository.getOtp(phoneNumber)
    }

    suspend operator fun invoke(user: User): Flow<ApiResult<Otp>> {
       return loginRepository.authenticateWithOtp(user)
    }

}