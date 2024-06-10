package ir.hirkancorp.presenter.screens.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.hirkancorp.domain.auth.use_cases.AuthUseCase
import ir.hirkancorp.domain.provider_profile.use_cases.ProviderProfileUseCase
import ir.hirkancorp.domain.provider_status.use_cases.ProviderStatusUseCase
import ir.hirkancorp.domain.utils.ApiResult.Error
import ir.hirkancorp.domain.utils.ApiResult.Loading
import ir.hirkancorp.domain.utils.ApiResult.Success
import ir.hirkancorp.presenter.screens.main.MainScreenEvent.CheckIfAuthenticate
import ir.hirkancorp.presenter.screens.main.MainScreenEvent.HandleMissedLocationPermission
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val authUseCase: AuthUseCase,
    private val providerProfileUseCase: ProviderProfileUseCase,
    private val providerStatusUseCase: ProviderStatusUseCase
) : ViewModel() {

    var state by mutableStateOf(MainScreenState())
        private set

    private var _authChannel = Channel<Boolean>()
    var authChannel = _authChannel.receiveAsFlow()


    fun onEvent(event: MainScreenEvent) = when(event) {
        is CheckIfAuthenticate -> isAuthenticate()
        is HandleMissedLocationPermission -> handleMissedLocationPermission(event.show)
        is MainScreenEvent.HandleMissedLocationPermissionError -> handleMissedLocationPermissionError(event.show)
        is MainScreenEvent.GetProviderProfile -> getProviderProfile()
        is MainScreenEvent.UpdateProviderStatus -> updateProviderStatus(event.isOnline)
        is MainScreenEvent.UpdateLocation -> updateLocation()
        is MainScreenEvent.ShowProviderStatusDialog -> showProviderStatusDialog(event.show, event.message)
    }

    private fun showProviderStatusDialog(show: Boolean, message: String) {
        state = state.copy(
            providerStatusDialogMessage = message,
            providerStatusDialog = show
        )
    }

    private fun updateProviderStatus(isOnline: Boolean) {
        viewModelScope.launch {
            providerStatusUseCase.invoke(isOnline).collect { result ->
                when(result) {
                    is Loading -> state = state.copy(providerStatus = ProviderStatus.Loading)
                    is Success -> {
                        state = state.copy(providerStatus = ProviderStatus.Success)
                        onEvent(MainScreenEvent.GetProviderProfile)
                    }
                    is Error -> state =  state.copy(providerStatus = ProviderStatus.Error(result.message.orEmpty()))
                }
            }
        }
    }

    private fun getProviderProfile() {
        viewModelScope.launch {
            providerProfileUseCase.invoke().collect { result ->
                state = state.copy(
                    profileState = when(result) {
                        is Error ->  ProviderProfileState.Error(result.message.orEmpty())
                        is Loading ->  ProviderProfileState.Loading
                        is Success -> { ProviderProfileState.Success(result.data) }
                    }
                )
            }
        }
    }

    private fun updateLocation() {
        viewModelScope.launch {

        }
    }

    private fun handleMissedLocationPermissionError(show: Boolean) {
        state = state.copy(missedLocationPermission = show)
    }

    private fun handleMissedLocationPermission(show: Boolean) {
        state = state.copy(locationErrorDialog = show)
    }

    private fun isAuthenticate() {
        viewModelScope.launch {
            state = state.copy(isLoadingAuth = true)
            authUseCase.invoke().collect { isAuthenticate ->
                state = state.copy(isAuthenticate = isAuthenticate,isLoadingAuth = false)
                _authChannel.send(isAuthenticate)
            }
        }
    }



}