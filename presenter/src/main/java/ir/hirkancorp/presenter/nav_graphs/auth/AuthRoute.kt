package ir.hirkancorp.presenter.nav_graphs.auth

sealed class AuthRoute(val route: String) {

    companion object {
        const val PHONE_NUMBER = "phoneNumber"
        const val TOKEN = "token"
    }

    data object Login: AuthRoute(route = "login_screen")
    data object Register: AuthRoute(route = "register_screen/{$PHONE_NUMBER}") {

        fun createRoute(phoneNumber: String?) = "register_screen/$phoneNumber"
    }
    data object ProfileImage: AuthRoute(route = "profileImage/{$TOKEN}") {

        fun createRoute(token: String?) = "profileImage/$token"
    }

}
