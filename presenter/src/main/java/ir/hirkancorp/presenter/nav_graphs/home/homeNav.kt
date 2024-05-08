package ir.hirkancorp.presenter.nav_graphs.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ir.hirkancorp.presenter.nav_graphs.Graphs
import ir.hirkancorp.presenter.screens.main.MainScreen

fun NavGraphBuilder.homeNav(navHostController: NavHostController) {
    navigation(
        route = Graphs.HOME,
        startDestination = HomeRoute.Home.route
    ) {
        composable(route = HomeRoute.Home.route) {
            MainScreen()
        }
    }
}