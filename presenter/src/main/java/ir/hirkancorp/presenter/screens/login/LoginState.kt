package ir.hirkancorp.presenter.screens.login

data class LoginState(
    val isLoading: Boolean = false,
    val phoneNumber: String? = null,
    val otpSent: Boolean = false,
    val userId: Int? = 0,
    val resendOtpTimer: String? = null,
    val navigate: Boolean = false,
    val success: String? = null,
    val error: String? = null
)
