package ir.hirkancorp.presenter.screens.main

data class MainScreenState(
    val isLoadingAuth: Boolean = false,
    val isAuthenticate: Boolean = true,
    val locationErrorDialog: Boolean = false,
    val missedLocationPermissionDialog: Boolean = false,
    val missedLocationPermission: Boolean = false,
)
