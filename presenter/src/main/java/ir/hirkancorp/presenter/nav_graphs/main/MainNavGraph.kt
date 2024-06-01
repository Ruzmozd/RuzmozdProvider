package ir.hirkancorp.presenter.nav_graphs.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ir.hirkancorp.presenter.nav_graphs.Graphs
import ir.hirkancorp.presenter.nav_graphs.auth.authNav
import ir.hirkancorp.presenter.nav_graphs.settings.settingsNav
import ir.hirkancorp.presenter.screens.main.MainScreen
import ir.hirkancorp.presenter.screens.requests.RequestsScreen

@Composable
fun MainNavGraph(
    modifier: Modifier = Modifier,
    navHostController: NavHostController
) {
    NavHost(
        modifier = modifier,
        navController = navHostController,
        route = Graphs.MAIN,
        startDestination = MainScreens.MainScreen.route
    ) {
        composable(route = MainScreens.MainScreen.route) {
            MainScreen(
                navigateToLoginScreen = {
                    navHostController.run {
                        popBackStack()
                        navigate(Graphs.AUTH)
                    }
                }
            )
        }
        composable(route = MainScreens.RequestsScreen.route) {
            RequestsScreen()
        }
        settingsNav(navHostController)
        authNav(navHostController)
    }
}