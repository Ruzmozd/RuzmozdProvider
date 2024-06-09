package ir.hirkancorp.domain.provider_profile.models

data class ProviderProfile(
    val checkedProviderAvailability: Boolean = false,
    val checkedProviderServiceType: Boolean = false,
    val city: String = "",
    val completedJobsCount: String = "",
    val firstName: String = "",
    val id: Int = 0,
    val isOnline: Boolean = false,
    val lastName: String = "",
    val location: Location = Location(),
    val mobileNumber: String = "",
    val nationalityCode: String = "",
    val pendingJobsCount: String = "",
    val profileImage: String = "",
    val serviceDescription: String = "",
    val status: ProviderStatusEnum = ProviderStatusEnum.INACTIVE,
    val statusDescription: String = ""
)
enum class ProviderStatusEnum {
    ACTIVE,
    INACTIVE,
    PENDING,
    REJECTED
}