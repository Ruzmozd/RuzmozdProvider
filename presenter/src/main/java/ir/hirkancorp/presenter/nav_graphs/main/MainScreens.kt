package ir.hirkancorp.presenter.nav_graphs.main

sealed class MainScreens(val route: String) {
    data object MainScreen: MainScreens(route = "main_screen")
}
