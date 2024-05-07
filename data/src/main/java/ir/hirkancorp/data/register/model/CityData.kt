package ir.hirkancorp.data.register.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CityData(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String
)