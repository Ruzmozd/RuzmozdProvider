package ir.hirkancorp.presenter.nav_graphs.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ir.hirkancorp.domain.job_progress.use_cases.JobProgressUseCase
import ir.hirkancorp.presenter.nav_graphs.Graphs
import ir.hirkancorp.presenter.nav_graphs.auth.authNav
import ir.hirkancorp.presenter.nav_graphs.settings.settingsNav
import ir.hirkancorp.presenter.screens.job_progress.JobProgressScreen
import ir.hirkancorp.presenter.screens.job_progress.JobProgressScreenViewModel
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
                },
                navigateToJobScreen = { jobId ->
                    navHostController.navigate(MainScreens.JobProgressScreen.createRoute(jobId = jobId))
                }
            )
        }
        composable(route = MainScreens.MainScreen.route) {
            val jobId = it.arguments?.getString(MainScreens.JOB_ID)?.toInt() ?: 0
            JobProgressScreen(jobId = jobId)
        }
        composable(route = MainScreens.RequestsScreen.route) {
            RequestsScreen()
        }
        settingsNav(navHostController)
        authNav(navHostController)
    }
}