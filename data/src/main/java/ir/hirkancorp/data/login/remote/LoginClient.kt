package ir.hirkancorp.data.login.remote

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import ir.hirkancorp.domain.login.model.User

class LoginClient(private val httpClient: HttpClient) {

    suspend fun sendOtp(mobileNumber: String) = httpClient.get {
        url("sendOtp")
        parameter("mobile_number", mobileNumber)
    }

    suspend fun loginOtp(user: User) = httpClient.get {
        url("loginOtp")
        parameter("mobile_number", user.phoneNumber)
        parameter("otp", user.otp)
        parameter("user_type", user.userType)
    }

}
