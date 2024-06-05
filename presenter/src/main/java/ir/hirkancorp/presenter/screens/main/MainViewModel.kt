package ir.hirkancorp.presenter.screens.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.hirkancorp.domain.auth.use_cases.AuthUseCase
import ir.hirkancorp.presenter.screens.main.MainScreenEvent.CheckIfAuthenticate
import ir.hirkancorp.presenter.screens.main.MainScreenEvent.HandleMissedLocationPermission
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MainViewModel(private val authUseCase: AuthUseCase) : ViewModel() {

    var state by mutableStateOf(MainScreenState())
        private set

    private var _authChannel = Channel<Boolean>()
    var authChannel = _authChannel.receiveAsFlow()


    fun onEvent(event: MainScreenEvent) = when(event) {
        is CheckIfAuthenticate -> isAuthenticate()
        is HandleMissedLocationPermission -> handleMissedLocationPermission(event.show)
        is MainScreenEvent.HandleMissedLocationPermissionError -> handleMissedLocationPermissionError(event.show)
        MainScreenEvent.UpdateLocation -> {}
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