package ir.hirkancorp.presenter.screens.main

sealed class MainScreenEvent {

    data object CheckIfAuthenticate: MainScreenEvent()
    data class HandleMissedLocationPermission(val show: Boolean): MainScreenEvent()
    data class HandleMissedLocationPermissionError(val show: Boolean) : MainScreenEvent()
    data object UpdateLocation: MainScreenEvent()
    data object GetProviderProfile: MainScreenEvent()

}