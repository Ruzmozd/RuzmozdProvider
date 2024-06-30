package ir.hirkancorp.presenter.screens.requests

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import ir.hirkancrop.domain.requests.use_case.RequestsUseCase
import kotlinx.coroutines.launch

class RequestsScreenViewModel(private val requestsUseCase: RequestsUseCase) : ViewModel() {

    var state by mutableStateOf(RequestsScreenState())
        private set

    fun onEvent(event: RequestsScreenEvent) = when(event) {
        RequestsScreenEvent.LoadRequests -> fetchRequests()
    }

    private fun fetchRequests() {
        state = state.copy(
            requests = requestsUseCase.invoke(null)
        )
    }


}