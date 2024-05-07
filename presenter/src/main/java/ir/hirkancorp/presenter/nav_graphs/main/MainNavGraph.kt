package ir.hirkancorp.presenter.nav_graphs.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import ir.hirkancorp.presenter.nav_graphs.Graphs
import ir.hirkancorp.presenter.nav_graphs.auth.authNav
import ir.hirkancorp.presenter.nav_graphs.home.homeNav

@Composable
fun MainNavGraph(
    modifier: Modifier = Modifier,
    navHostController: NavHostController
) {
    NavHost(
        modifier = modifier,
        navController = navHostController,
        route = Graphs.MAIN,
        startDestination = Graphs.AUTH
    ) {
        homeNav(navHostController)
        authNav(navHostController)
    }
}