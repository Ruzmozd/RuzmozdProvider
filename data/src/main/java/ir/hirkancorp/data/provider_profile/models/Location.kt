package ir.hirkancorp.data.provider_profile.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Location(
    @SerialName("latitude")
    val latitude: String = "",
    @SerialName("longitude")
    val longitude: String = "",
    @SerialName("work_radius")
    val workRadius: Int = 0
)