package ir.hirkancorp.presenter.screens.main

import android.os.Bundle
import ir.hirkancorp.domain.request.model.BookJob

sealed class MainScreenEvent {

    data object CheckIfAuthenticate: MainScreenEvent()
    data class HandleMissedLocationPermission(val show: Boolean): MainScreenEvent()
    data class HandleMissedLocationPermissionError(val show: Boolean) : MainScreenEvent()
    data class HandleMissedNotificationPermission(val show: Boolean) : MainScreenEvent()
    data class UpdateLocation(val location: Pair<Double, Double>): MainScreenEvent()
    data object GetProviderProfile: MainScreenEvent()
    data class UpdateProviderStatus(val isOnline: Boolean): MainScreenEvent()
    data class ShowProviderStatusDialog(val show: Boolean, val message: String): MainScreenEvent()
    data class UpdateDevice(val deviceId: String): MainScreenEvent()
    data class UpdateWorkRadius(val radius: Int) : MainScreenEvent()
    data class HandleNotification(val bundle: Bundle) : MainScreenEvent()
    data class ShowJobRequestDialog(val show: Boolean, val job: BookJob?) : MainScreenEvent()
    data class AcceptRequest(val requestId: Int) : MainScreenEvent()

}