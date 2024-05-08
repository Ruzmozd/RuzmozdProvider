package ir.hirkancorp.data.register.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CityListData(
    @SerialName("cities")
    val cities: List<CityData>
)