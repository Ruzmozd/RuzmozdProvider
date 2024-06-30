package ir.hirkancorp.presenter.screens.settings

sealed class SettingsScreenEvent {

    data object LogOut: SettingsScreenEvent()
    data object ShowDialog: SettingsScreenEvent()
    data object HideDialog: SettingsScreenEvent()

}