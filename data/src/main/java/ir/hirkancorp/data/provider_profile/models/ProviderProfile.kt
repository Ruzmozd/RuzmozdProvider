package ir.hirkancorp.data.provider_profile.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProviderProfile(
    @SerialName("provider_profile")
    val providerProfile: ProviderProfileX = ProviderProfileX()
)