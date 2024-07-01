package ir.hirkancorp.presenter.nav_graphs.settings

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ir.hirkancorp.presenter.nav_graphs.Graphs
import ir.hirkancorp.presenter.screens.profile.ProfileScreen
import ir.hirkancorp.presenter.screens.settings.SettingsScreen

fun NavGraphBuilder.settingsNav(navHostController: NavHostController) {
    navigation(
       route = Graphs.SETTINGS,
        startDestination = SettingsScreens.SettingsScreen.route
    ) {
        composable(route = SettingsScreens.SettingsScreen.route) {
            SettingsScreen(
                navigateTo = { route ->
                    route?.let {
                        navHostController.navigate(route = route)
                    } ?: navHostController.navigateUp()
                }
            )
        }
        composable(route = SettingsScreens.ProfileScreen.route) {
            ProfileScreen(
                navigateUp = navHostController::navigateUp
            )
        }
    }
}