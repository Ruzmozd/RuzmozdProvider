package ir.hirkancorp.presenter.screens.job_progress

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.hirkancorp.domain.job_progress.use_cases.JobProgressUseCase
import ir.hirkancorp.domain.utils.ApiResult.Error
import ir.hirkancorp.domain.utils.ApiResult.Loading
import ir.hirkancorp.domain.utils.ApiResult.Success
import ir.hirkancorp.presenter.core.state.HttpRequestState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class JobProgressScreenViewModel(
    private val jobProgressUseCase: JobProgressUseCase,
) : ViewModel(), KoinComponent {

    private val _navigateToHomeScreen = Channel<Pair<String, Boolean>>()
    val navigateToHomeScreen = _navigateToHomeScreen.receiveAsFlow()

    var state by mutableStateOf(JobProgressScreenState())
        private set

    fun onEvent(event: JobProgressScreenEvent) = when (event) {
        is JobProgressScreenEvent.GetJobProgress -> getJobProgress(event.jobId)
        is JobProgressScreenEvent.JobCompleted -> showCompleteDialog(event.message)
        is JobProgressScreenEvent.HideDialog -> closeDialog()
        is JobProgressScreenEvent.Rate -> {}
    }

    private fun showCompleteDialog(message: String) {
        state = state.copy(
            showDialog = true,
            dialog = JobProgressScreenDialog.JobCompleted(message = message)
        )
    }

    private fun closeDialog() {
        state = state.copy(showDialog = false)
    }

    private fun getJobProgress(jobId: Int) {
        state = state.copy(jobId = jobId)
        viewModelScope.launch {
            jobProgressUseCase.invoke(jobId = jobId).collect { result ->
                state = when (result) {
                    is Loading -> state
                    is Success -> state.copy(jobProgress = HttpRequestState.ResponseState(result.data?.jobProgress))
                    is Error -> state.copy(jobProgress = HttpRequestState.ErrorState(result.message))
                }
            }
        }
    }


}