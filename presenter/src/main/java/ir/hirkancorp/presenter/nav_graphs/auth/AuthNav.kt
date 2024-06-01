package ir.hirkancorp.presenter.nav_graphs.auth

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ir.hirkancorp.presenter.nav_graphs.Graphs
import ir.hirkancorp.presenter.nav_graphs.auth.AuthRoute.Login
import ir.hirkancorp.presenter.nav_graphs.auth.AuthRoute.ProfileImage
import ir.hirkancorp.presenter.nav_graphs.auth.AuthRoute.Register
import ir.hirkancorp.presenter.nav_graphs.auth.AuthRoute.UploadDocument
import ir.hirkancorp.presenter.nav_graphs.main.MainScreens
import ir.hirkancorp.presenter.screens.login.LoginScreen
import ir.hirkancorp.presenter.screens.profile_image.ProfileImageScreen
import ir.hirkancorp.presenter.screens.register.RegisterScreen
import ir.hirkancorp.presenter.screens.upload_document.UploadDocumentScreen

fun NavGraphBuilder.authNav(navHostController: NavHostController) {
    navigation(
        route = Graphs.AUTH,
        startDestination = Login.route
    ) {
        composable(route = Login.route) {
            LoginScreen(
                navigateToRegisterScreen = { phoneNumber ->
                    navHostController.navigate(Register.createRoute(phoneNumber))
                },
                navigateToMainScreen = { navHostController.navigate(MainScreens.MainScreen.route) }
            )
        }
        composable(route = Register.route) { navBackStackEntity ->
            val phoneNumber = navBackStackEntity.arguments?.getString(AuthRoute.PHONE_NUMBER)
            RegisterScreen(
                phoneNumber = phoneNumber,
                navigateUp = { navHostController.popBackStack() },
                navigateToProfileImageScreen = { navHostController.navigate(ProfileImage.createRoute(it)) }
            )
        }
        composable(route = ProfileImage.route) {
            ProfileImageScreen {
                navHostController.navigate(route = UploadDocument.route)
            }
        }
        composable(route = UploadDocument.route) {
            UploadDocumentScreen {
                navHostController.navigate(MainScreens.MainScreen.route)
            }
        }
    }
}