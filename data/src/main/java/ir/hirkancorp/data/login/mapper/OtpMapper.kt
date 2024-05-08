package ir.hirkancorp.data.login.mapper

import ir.hirkancorp.domain.login.model.Otp
import ir.hirkancorp.data.login.model.OtpData

fun OtpData.toDomain(): Otp = Otp(
    userId = userId
)