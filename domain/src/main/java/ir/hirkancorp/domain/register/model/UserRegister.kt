package ir.hirkancorp.domain.register.model

data class UserRegister(
    val phoneNumber: String,
    val userType: String = "Provider",
    val firstName: String,
    val lastName: String,
    val city: Int = 0,
    val deviceType: Int = 2,
    val nationalityCode: String? = "",
    val nationality: String? = "",
    val referralCode: String? = "",
)
