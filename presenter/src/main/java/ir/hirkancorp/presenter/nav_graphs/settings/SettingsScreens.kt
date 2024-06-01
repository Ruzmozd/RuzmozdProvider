package ir.hirkancorp.presenter.nav_graphs.settings

sealed class SettingsScreens(val route: String) {

    data object SettingsScreen : SettingsScreens(route = "setting_screen")

}