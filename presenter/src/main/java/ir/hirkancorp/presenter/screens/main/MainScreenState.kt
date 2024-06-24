package ir.hirkancorp.presenter.screens.main

import ir.hirkancorp.domain.provider_profile.models.ProviderProfile
import ir.hirkancorp.domain.request.model.BookJob

data class MainScreenState(
    val isLoadingAuth: Boolean = false,
    val isAuthenticate: Boolean = false,
    val locationErrorDialog: Boolean = false,
    val missedLocationPermissionDialog: Boolean = false,
    val missedLocationPermission: Boolean = false,
    val missedNotificationPermission: Boolean = false,
    val profileState: ProviderProfileState = ProviderProfileState.Loading,
    val providerStatus: ProviderStatus = ProviderStatus.Idle,
    val providerStatusDialogMessage: String = "",
    val providerStatusDialog: Boolean = false,
    val updateDeviceLoading: Boolean = false,
    val requestNotificationState: NotificationEvent = NotificationEvent.Idle,
    val showJobRequestDialog: Boolean = false,
    val acceptRequestLoading: Boolean = false,
    val declineRequestLoading: Boolean = false,
    val timerState: String = "00:00",
    val job: BookJob? = null,
    val requestId: Int = 0,
    val jobId: Int = 0,
)

sealed class ProviderProfileState {
    data object Loading : ProviderProfileState()
    data class Error(val message: String) : ProviderProfileState()
    data class Success(val providerProfile: ProviderProfile?) : ProviderProfileState()

}

sealed class ProviderStatus {
    data object Idle : ProviderStatus()
    data object Loading : ProviderStatus()
    data class Error(val message: String) : ProviderStatus()
    data object Success : ProviderStatus()
}


sealed class NotificationEvent {
    data object Idle: NotificationEvent()
    data class JobRequest(val job: BookJob): NotificationEvent()
    data object CancelRequest : NotificationEvent()
    data class CancelJob(val jobId: Int): NotificationEvent()
}