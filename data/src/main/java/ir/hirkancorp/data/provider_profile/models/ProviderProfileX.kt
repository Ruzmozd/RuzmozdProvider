package ir.hirkancorp.data.provider_profile.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProviderProfileX(
    @SerialName("checked_provider_availability")
    val checkedProviderAvailability: Boolean = false,
    @SerialName("checked_provider_service_type")
    val checkedProviderServiceType: Boolean = false,
    @SerialName("city")
    val city: String = "",
    @SerialName("completed_jobs_count")
    val completedJobsCount: String = "",
    @SerialName("first_name")
    val firstName: String = "",
    @SerialName("id")
    val id: Int = 0,
    @SerialName("is_online")
    val isOnline: Boolean = false,
    @SerialName("last_name")
    val lastName: String = "",
    @SerialName("location")
    val location: Location = Location(),
    @SerialName("mobile_number")
    val mobileNumber: String = "",
    @SerialName("nationality_code")
    val nationalityCode: String = "",
    @SerialName("pending_jobs_count")
    val pendingJobsCount: String = "",
    @SerialName("profile_image")
    val profileImage: String = "",
    @SerialName("service_description")
    val serviceDescription: String = "",
    @SerialName("status")
    val status: String = ""
)