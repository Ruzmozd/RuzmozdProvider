package ir.hirkancorp.presenter.screens.profile

data class ProfileScreenState(
    val isLoading: Boolean = false,
    val inEditMode: Boolean = false,
    val profileUpdateMessage: String = "",
    val firstName: String = "",
    val firstNameError: String = "",
    val lastName: String = "",
    val lastNameError: String = "",
    val image: List<Byte> = emptyList(),
    val imageUrl: String = "",
    val mobileNumber: String = "",
    val nationality: String = "",
)
