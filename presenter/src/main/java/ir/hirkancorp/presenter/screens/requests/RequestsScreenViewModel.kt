package ir.hirkancorp.presenter.screens.requests

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import ir.hirkancorp.domain.requests.use_case.RequestsUseCase

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