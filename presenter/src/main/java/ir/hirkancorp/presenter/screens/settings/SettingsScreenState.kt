package ir.hirkancorp.presenter.screens.settings

data class SettingsScreenState(
    val isLoading: Boolean = false,
    val showDialog: Boolean = false,
    val isLoggedOut: Boolean = false,
)