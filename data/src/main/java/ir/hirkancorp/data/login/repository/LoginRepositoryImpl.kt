package ir.hirkancorp.data.login.repository

import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode.Companion.Accepted
import io.ktor.http.HttpStatusCode.Companion.InternalServerError
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.http.HttpStatusCode.Companion.Unauthorized
import io.ktor.http.HttpStatusCode.Companion.UnprocessableEntity
import ir.hirkancorp.data.common.api_utils.commonRequest
import ir.hirkancorp.data.common.model.HttpResponseModel
import ir.hirkancorp.data.login.mapper.toDomain
import ir.hirkancorp.data.login.model.OtpData
import ir.hirkancorp.data.login.remote.LoginClient
import ir.hirkancorp.data.preferences.repository.LocalServiceRepository
import ir.hirkancorp.domain.login.model.Otp
import ir.hirkancorp.domain.login.model.User
import ir.hirkancorp.domain.login.repository.LoginRepository
import ir.hirkancorp.domain.utils.ApiResult
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent

class LoginRepositoryImpl(
    private val client: LoginClient,
    private val localServiceRepository: LocalServiceRepository
) : LoginRepository, KoinComponent {

    override suspend fun getOtp(phoneNumber: String): Flow<ApiResult<String>> = commonRequest(
        httpResponse = { client.sendOtp(phoneNumber) },
        errorCodes = listOf(UnprocessableEntity,InternalServerError),
        successAction = Pair(OK){ httpResponse->
            httpResponse.body<HttpResponseModel<OtpData>>().statusMessage
        }
    )

    override suspend fun authenticateWithOtp(user: User): Flow<ApiResult<Otp>> = commonRequest(
        httpResponse = { client.loginOtp(user) },
        errorCodes = listOf(Unauthorized,UnprocessableEntity,InternalServerError),
        successAction = Pair(Accepted){ httpResponse ->
            val otp = httpResponse.body<HttpResponseModel<OtpData>>()
            otp.data.run {
                if (userId != null && userId > 0) {
                    saveAccessToken(accessToken)
                }
            }
            otp.data.toDomain()
        }
    )

    private suspend fun saveAccessToken(accessToken: String) {
        localServiceRepository.saveAccessToken(accessToken)
    }

}