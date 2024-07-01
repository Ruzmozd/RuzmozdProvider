package ir.hirkancorp.data.provider_profile.mapper

import ir.hirkancorp.data.provider_profile.models.Location
import ir.hirkancorp.data.provider_profile.models.ProviderProfileData
import ir.hirkancorp.domain.provider_profile.models.ProviderProfile
import ir.hirkancorp.domain.provider_profile.models.ProviderStatusEnum
import ir.hirkancorp.domain.provider_profile.models.Location as LocationDomain

fun ProviderProfileData.toDomain(): ProviderProfile = ProviderProfile(
    checkedProviderAvailability = checkedProviderAvailability,
    checkedProviderServiceType = checkedProviderServiceType,
    city = city,
    completedJobsCount = completedJobsCount,
    firstName = firstName,
    id = id,
    isOnline = isOnline,
    lastName = lastName,
    location = location.toDomain(),
    mobileNumber = mobileNumber,
    nationality = nationality,
    nationalityCode = nationalityCode,
    pendingJobsCount = pendingJobsCount,
    profileImage = profileImage,
    serviceDescription = serviceDescription,
    status =  status.toStatusEnum()

)

private fun Location.toDomain(): LocationDomain = LocationDomain(
    latitude = latitude,
    longitude = longitude,
    workRadius = workRadius

)

fun String.toStatusEnum(): ProviderStatusEnum = when (this) {
    "Active" -> ProviderStatusEnum.ACTIVE
    "Inactive" -> ProviderStatusEnum.INACTIVE
    "Pending" -> ProviderStatusEnum.PENDING
    "rejected" -> ProviderStatusEnum.REJECTED
    else ->  ProviderStatusEnum.INACTIVE
}