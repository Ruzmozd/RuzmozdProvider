package ir.hirkancorp.presenter.screens.profile_image

sealed class ProfileImageEvent {

  data class SendUploadImageEvent (val image: List<Byte>):ProfileImageEvent()
}