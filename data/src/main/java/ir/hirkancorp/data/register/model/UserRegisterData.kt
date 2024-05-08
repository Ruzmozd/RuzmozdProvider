package ir.hirkancorp.data.register.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserRegisterData(
    @SerialName("access_token")
    val accessToken: String = "",
    @SerialName("user_id")
    val userId: Int? = 0
)
