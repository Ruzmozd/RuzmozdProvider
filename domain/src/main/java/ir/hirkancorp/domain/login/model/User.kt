package ir.hirkancorp.domain.login.model

data class User(
    val phoneNumber: String,
    val otp: String,
    val userType: String = "Provider"
)
