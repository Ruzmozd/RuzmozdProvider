package ir.hirkancorp.data.register.remote

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import ir.hirkancorp.domain.register.model.UserRegister

class RegisterClient(private val httpClient: HttpClient) {

    suspend fun register(user: UserRegister) = httpClient.get {
        url("register")
        parameter("mobile_number", user.phoneNumber)
        parameter("user_type", user.userType)
        parameter("first_name", user.firstName)
        parameter("last_name", user.lastName)
        parameter("city", user.city)
        parameter("device_type", user.deviceType)
        parameter("nationality_code", user.nationalityCode)
        parameter("nationality", user.nationality)
        parameter("referral_code", user.referralCode)
    }

    suspend fun getCityList() = httpClient.get {
        url("get_cities_list")
    }

}