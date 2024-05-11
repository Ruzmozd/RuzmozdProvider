package ir.hirkancorp.presenter.screens.login

sealed class LoginEvent {
    data class SendOtpRequest(val phoneNumber: String) : LoginEvent()
    data class AuthenticateOtp(val otp: String) : LoginEvent()
}