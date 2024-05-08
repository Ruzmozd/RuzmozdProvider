package ir.hirkancorp.data.register.mapper

import ir.hirkancorp.data.common.model.HttpResponseModel
import ir.hirkancorp.data.register.model.UserRegisterData
import ir.hirkancorp.domain.register.model.UserRegisterResult

fun HttpResponseModel<UserRegisterData>.toDomain(): UserRegisterResult = UserRegisterResult(
    userId = data.userId,
    token = data.accessToken,
    statusMessage = statusMessage
)