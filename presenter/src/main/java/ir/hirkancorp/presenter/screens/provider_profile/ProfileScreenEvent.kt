package ir.hirkancorp.presenter.screens.profile

sealed class ProfileScreenEvent() {
    data class SetEditMode(val inEditMode: Boolean): ProfileScreenEvent()
    data class UpdateFirstName(val firstName: String): ProfileScreenEvent()
    data class UpdateLastName(val lastName: String): ProfileScreenEvent()
    data class UpdateImage(val image: List<Byte>): ProfileScreenEvent()
    data object UpdateProfile: ProfileScreenEvent()

}
