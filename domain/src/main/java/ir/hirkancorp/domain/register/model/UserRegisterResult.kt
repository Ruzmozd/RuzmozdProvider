package ir.hirkancorp.domain.register.model

data class UserRegisterResult(
    val userId: Int? = 0,
    val token : String?,
    val statusMessage :String?
)
