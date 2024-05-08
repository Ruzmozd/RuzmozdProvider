package ir.hirkancorp.presenter.nav_graphs.auth

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ir.hirkancorp.presenter.nav_graphs.Graphs
import ir.hirkancorp.presenter.nav_graphs.home.HomeRoute
import ir.hirkancorp.presenter.screens.login.LoginScreen
import ir.hirkancorp.presenter.screens.profile_image.ProfileImageScreen
import ir.hirkancorp.presenter.screens.register.RegisterScreen

fun NavGraphBuilder.authNav(navHostController: NavHostController) {
    navigation(
        route = Graphs.AUTH,
        startDestination = AuthRoute.Login.route
    ) {
        composable(route = AuthRoute.Login.route) {
            LoginScreen(
                navigateToRegisterScreen = { phoneNumber ->
                    navHostController.navigate(AuthRoute.Register.createRoute(phoneNumber))
                },
                navigateToMainScreen = { navHostController.navigate(HomeRoute.Home.route) }
            )
        }
        composable(route = AuthRoute.Register.route) { navBackStackEntity ->
            val phoneNumber = navBackStackEntity.arguments?.getString(AuthRoute.PHONE_NUMBER)
            RegisterScreen(
                phoneNumber = phoneNumber,
                navigateUp = { navHostController.popBackStack() },
                navigateToProfileImageScreen = { navHostController.navigate(AuthRoute.ProfileImage.createRoute(it)) }
            )
        }
        composable(route = AuthRoute.ProfileImage.route) {
            ProfileImageScreen()
        }
    }
}