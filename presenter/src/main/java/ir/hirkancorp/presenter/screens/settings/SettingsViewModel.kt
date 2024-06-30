package ir.hirkancorp.presenter.screens.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.hirkancorp.domain.logout.use_case.LogOutUseCase
import kotlinx.coroutines.launch

class SettingsViewModel(private val logOutUseCase: LogOutUseCase): ViewModel() {

    var state by mutableStateOf(SettingsScreenState())
        private set

    fun onEvent(event: SettingsScreenEvent) = when(event) {
        SettingsScreenEvent.ShowDialog -> showDialog()
        SettingsScreenEvent.LogOut -> logOut()
        SettingsScreenEvent.HideDialog -> hideDialog()
    }

    private fun showDialog() {
        state = state.copy(
            showDialog = true
        )
    }

    private fun hideDialog() {
        state = state.copy(
            showDialog = false
        )
    }

    private fun logOut() {
        state = state.copy(isLoading = true)
        viewModelScope.launch {
            logOutUseCase.invoke()
            state = state.copy(
                isLoading = false,
                showDialog = false,
                isLoggedOut = true
            )
        }
    }

}