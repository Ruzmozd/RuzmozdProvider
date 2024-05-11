package ir.hirkancorp.domain.login.repository

import ir.hirkancorp.domain.login.model.Otp
import ir.hirkancorp.domain.login.model.User
import kotlinx.coroutines.flow.Flow
import ir.hirkancorp.domain.utils.ApiResult

interface LoginRepository {

    suspend fun getOtp(phoneNumber: String): Flow<ApiResult<String>>

    suspend fun authenticateWithOtp(user: User): Flow<ApiResult<Otp>>

}