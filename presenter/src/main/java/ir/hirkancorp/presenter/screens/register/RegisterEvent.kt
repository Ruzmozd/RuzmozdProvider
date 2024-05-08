package ir.hirkancorp.presenter.screens.register

sealed class RegisterEvent() {

    data class SendRegisterEvent(val phoneNumber: String):RegisterEvent()
    data class UpdateName(val name:String):RegisterEvent()
    data class UpdateLastName(val lastName: String):RegisterEvent()
    data class UpdateCity(val city: Pair<Int, String>):RegisterEvent()
    data class UpdateNationalCode(val nationalCode: String):RegisterEvent()
    data class UpdateReferralCode(val referralCode: String):RegisterEvent()
}
