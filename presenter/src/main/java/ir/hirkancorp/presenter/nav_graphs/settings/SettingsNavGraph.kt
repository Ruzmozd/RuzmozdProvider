package ir.hirkancorp.presenter.nav_graphs.settings

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ir.hirkancorp.presenter.nav_graphs.Graphs
import ir.hirkancorp.presenter.screens.requests.SettingsScreen

fun NavGraphBuilder.settingsNav(navHostController: NavHostController) {
    navigation(
       route = Graphs.SETTINGS,
        startDestination = SettingsScreens.SettingsScreen.route
    ) {
        composable(route = SettingsScreens.SettingsScreen.route) {
            SettingsScreen()
        }
    }
}