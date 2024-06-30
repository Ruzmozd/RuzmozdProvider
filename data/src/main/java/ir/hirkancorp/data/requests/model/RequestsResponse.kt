package ir.hirkancorp.data.requests.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestsData(
    @SerialName("services")
    val serviceData: List<ServiceData> = emptyList()
)

@Serializable
data class ServiceData(
    @SerialName("booking_type")
    val bookingType: String = "",
    @SerialName("job_id")
    val jobId: Int = 0,
    @SerialName("job_location")
    val jobLocation: String = "",
    @SerialName("price_type")
    val priceType: String = "",
    @SerialName("promo_id")
    val promoId: Int = 0,
    @SerialName("provider_id")
    val providerId: Int = 0,
    @SerialName("request_id")
    val requestId: Int = 0,
    @SerialName("schedule_date")
    val scheduleDate: String = "",
    @SerialName("schedule_display_date")
    val scheduleDisplayDate: String = "",
    @SerialName("schedule_time")
    val scheduleTime: String = "",
    @SerialName("status")
    val status: String = "",
    @SerialName("user_id")
    val userId: Int = 0
)
