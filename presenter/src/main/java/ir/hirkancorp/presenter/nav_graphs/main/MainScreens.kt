package ir.hirkancorp.presenter.nav_graphs.main

sealed class MainScreens(val route: String) {
    data object MainScreen : MainScreens(route = "main_screen")
    data object RequestsScreen : MainScreens(route = "requests_screen")
    data object SettingsScreen : MainScreens(route = "settings_screen")
}
