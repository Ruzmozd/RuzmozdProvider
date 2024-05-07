package ir.hirkancorp.presenter.nav_graphs.home

sealed class HomeRoute(val route: String) {
    data object Home : HomeRoute(route = "home_screen")
}
