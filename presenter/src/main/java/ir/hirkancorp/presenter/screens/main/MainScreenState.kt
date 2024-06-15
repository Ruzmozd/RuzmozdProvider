package ir.hirkancorp.presenter.screens.main

import ir.hirkancorp.domain.provider_profile.models.ProviderProfile

data class MainScreenState(
    val isLoadingAuth: Boolean = false,
    val isAuthenticate: Boolean = false,
    val locationErrorDialog: Boolean = false,
    val missedLocationPermissionDialog: Boolean = false,
    val missedLocationPermission: Boolean = false,
    val profileState: ProviderProfileState = ProviderProfileState.Loading,
    val providerStatus: ProviderStatus = ProviderStatus.Idle,
    val providerStatusDialogMessage: String = "",
    val providerStatusDialog: Boolean = false,
    val updateDeviceLoading: Boolean = false,
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