package ir.hirkancorp.data.login.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OtpData(
    @SerialName("access_token")
    val accessToken: String = "",
    @SerialName("user_id")
    val userId: Int? = 0
)
