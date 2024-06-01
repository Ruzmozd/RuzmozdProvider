package ir.hirkancorp.presenter.core.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import ir.hirkancorp.presenter.nav_graphs.Graphs
import ir.hirkancorp.presenter.nav_graphs.main.MainScreens

sealed class BottomNavigationScreens(
    val route: String,
    val title: String,
    val icon: ImageVector
) {

    data class MainScreen(
        val screenTitle: String,
        val screenIcon: ImageVector) :
        BottomNavigationScreens(
            route = MainScreens.MainScreen.route,
            title = screenTitle,
            icon = screenIcon
        )
    data class RequestScreen(
        val screenTitle: String,
        val screenIcon: ImageVector) :
        BottomNavigationScreens(
            route = MainScreens.RequestsScreen.route,
            title = screenTitle,
            icon = screenIcon
        )
    data class SettingsScreen(
        val screenTitle: String,
        val screenIcon: ImageVector) :
        BottomNavigationScreens(
            route = Graphs.SETTINGS,
            title = screenTitle,
            icon = screenIcon
        )
}