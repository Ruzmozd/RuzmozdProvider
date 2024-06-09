package ir.hirkancorp.data.provider_status.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProviderStatus(
    @SerialName("is_online")
    val isOnline: Boolean = false
)
