package ir.hirkancorp.presenter.nav_graphs.settings

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ir.hirkancorp.domain.logout.use_case.LogOutUseCase
import ir.hirkancorp.presenter.nav_graphs.Graphs
import ir.hirkancorp.presenter.screens.settings.SettingsScreen
import ir.hirkancorp.presenter.screens.settings.SettingsViewModel

fun NavGraphBuilder.settingsNav(navHostController: NavHostController) {
    navigation(
       route = Graphs.SETTINGS,
        startDestination = SettingsScreens.SettingsScreen.route
    ) {
        composable(route = SettingsScreens.SettingsScreen.route) {
            SettingsScreen(
                navigateTo = {}
            )
        }
    }
}