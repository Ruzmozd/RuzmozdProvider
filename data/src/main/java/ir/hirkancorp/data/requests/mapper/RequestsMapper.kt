package ir.hirkancorp.data.requests.mapper

import ir.hirkancorp.data.requests.model.ServiceData
import ir.hirkancorp.domain.requests.model.Service

fun List<ServiceData>.toDomain(): List<Service> = map { it.toService() }


fun ServiceData.toService(): Service = Service(
    bookingType = bookingType,
    jobId = jobId,
    jobLocation = jobLocation,
    priceType = priceType,
    promoId = promoId,
    providerId = providerId,
    requestId = requestId,
    scheduleDate = scheduleDate,
    scheduleDisplayDate = scheduleDisplayDate,
    scheduleTime = scheduleTime,
    status = status,
    userId = userId
)