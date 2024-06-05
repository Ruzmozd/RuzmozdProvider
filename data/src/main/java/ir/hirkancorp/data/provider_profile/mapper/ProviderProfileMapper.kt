package ir.hirkancorp.data.provider_profile.mapper

import ir.hirkancorp.data.provider_profile.models.Location
import ir.hirkancorp.data.provider_profile.models.ProviderProfileX
import ir.hirkancorp.domain.provider_profile.models.ProviderProfile
import ir.hirkancorp.domain.provider_profile.models.Location as LocationDomain

fun ProviderProfileX.toDomain(): ProviderProfile = ProviderProfile(
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
    nationalityCode = nationalityCode,
    pendingJobsCount = pendingJobsCount,
    profileImage = profileImage,
    serviceDescription = serviceDescription,
    status = status

)

private fun Location.toDomain(): LocationDomain = LocationDomain(
    latitude = latitude,
    longitude = longitude,
    workRadius = workRadius

)