package ir.hirkancorp.presenter.screens.job_progress

import androidx.collection.mutableIntListOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.hirkancorp.domain.job_progress.use_cases.ArriveNowUseCase
import ir.hirkancorp.domain.job_progress.use_cases.BeginJobUseCase
import ir.hirkancorp.domain.job_progress.use_cases.CancelJobReasonsUseCase
import ir.hirkancorp.domain.job_progress.use_cases.CancelJobUseCase
import ir.hirkancorp.domain.job_progress.use_cases.EndJobUseCase
import ir.hirkancorp.domain.job_progress.use_cases.JobProgressUseCase
import ir.hirkancorp.domain.utils.ApiResult.Error
import ir.hirkancorp.domain.utils.ApiResult.Loading
import ir.hirkancorp.domain.utils.ApiResult.Success
import ir.hirkancorp.presenter.core.state.HttpRequestState
import ir.hirkancorp.presenter.core.utils.UiEvent
import ir.hirkancorp.presenter.nav_graphs.main.MainScreens
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class JobProgressScreenViewModel(
    private val jobProgressUseCase: JobProgressUseCase,
    private val arriveNowUseCase: ArriveNowUseCase,
    private val beginJobUseCase: BeginJobUseCase,
    private val endJobUseCase: EndJobUseCase,
    private val cancelJobReasonsUseCase: CancelJobReasonsUseCase,
    private val cancelJobUseCase: CancelJobUseCase
) : ViewModel(), KoinComponent {

    private val _navigateToHomeScreen = Channel<Pair<String, Boolean>>()
    val navigateToHomeScreen = _navigateToHomeScreen.receiveAsFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var state by mutableStateOf(JobProgressScreenState())
        private set

    fun onEvent(event: JobProgressScreenEvent) = when (event) {
        is JobProgressScreenEvent.GetJobProgress -> getJobProgress(event.jobId)
        is JobProgressScreenEvent.JobCompleted -> showCompleteDialog(event.message)
        is JobProgressScreenEvent.HideDialog -> closeDialog()
        is JobProgressScreenEvent.Rate -> {}
        JobProgressScreenEvent.NextStep -> goTonextStep()
        is JobProgressScreenEvent.CancelJob -> cancelJob(event.reasonId, event.comment)
        JobProgressScreenEvent.OpenCancelReasonsSheet -> openCancelReasonsSheet()
        JobProgressScreenEvent.OpenRatingsSheet -> openRatingSheet()
    }

    private fun openRatingSheet() {
        state = state.copy(bottomSheetType = BottomSheetType.RatingType)
    }

    private fun cancelJob(reasonId: Int, comment: String?) {
        state = state.copy(
            cancelReasonId = reasonId,
            cancelReasonComment = comment
        )
        viewModelScope.launch {
            cancelJobUseCase(
                jobId = state.jobId,
                reasonId = state.cancelReasonId,
                cancelComment = state.cancelReasonComment
            ).collect { result ->
                when(result) {
                    is Error -> _uiEvent.send(UiEvent.ShowSnackBar(result.message.orEmpty()))
                    is Loading -> state = state.copy(cancelJobLoading = true)
                    is Success -> {
                        state = state.copy(bottomSheetType = null)
                        _uiEvent.send(UiEvent.Navigate(MainScreens.MainScreen.route))
                    }
                }
            }
        }
    }

    private fun openCancelReasonsSheet() {
        state = state.copy(bottomSheetType = BottomSheetType.CancelJobType)
        viewModelScope.launch {
            cancelJobReasonsUseCase().collect { result ->
                state = when(result) {
                    is Error -> {
                        _uiEvent.send(UiEvent.ShowSnackBar(result.message.orEmpty()))
                        state.copy(
                            cancelReasons = HttpRequestState.ErrorState(message = result.message.orEmpty()),
                            bottomSheetType = null
                        )
                    }
                    is Loading -> state.copy(cancelReasons = HttpRequestState.LoadingState())
                    is Success -> state.copy(cancelReasons = HttpRequestState.ResponseState(result.data?.cancelJobReasons.orEmpty()))
                }
            }
        }
    }

    private fun goTonextStep() {
        when(state.nextStep) {
            1 -> arriveNow()
            2 -> beginJob()
            3 -> endJob()
        }
    }

    private fun arriveNow() {
        viewModelScope.launch {
            arriveNowUseCase.invoke(state.jobId).collect { result ->
                when(result) {
                    is Error -> Unit
                    is Loading -> state = state.copy(goToNextStep = true)
                    is Success -> {
                        state = state.copy(goToNextStep = false)
                        onEvent(JobProgressScreenEvent.GetJobProgress(state.jobId))
                    }
                }
            }
        }
    }

    private fun beginJob() {
        viewModelScope.launch {
            beginJobUseCase.invoke(state.jobId).collect { result ->
                when(result) {
                    is Error -> Unit
                    is Loading -> state = state.copy(goToNextStep = true)
                    is Success -> {
                        state = state.copy(goToNextStep = false)
                        onEvent(JobProgressScreenEvent.GetJobProgress(state.jobId))
                    }
                }
            }
        }
    }

    private fun endJob() {
        viewModelScope.launch {
            endJobUseCase.invoke(state.jobId).collect { result ->
                when(result) {
                    is Error -> Unit
                    is Loading -> state = state.copy(goToNextStep = true)
                    is Success -> {
                        state = state.copy(goToNextStep = false)
                        onEvent(JobProgressScreenEvent.GetJobProgress(state.jobId))
                    }
                }
            }
        }
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
                    is Success -> {
                        val passed = mutableIntListOf()
                        result.data?.jobProgress?.forEachIndexed { index, jobState ->
                            if (jobState.status) passed.add(index)
                        }
                        val last = passed.last().let { i -> if (i  < 3) i.plus(1) else i }
                        state.copy(
                            jobProgress = HttpRequestState.ResponseState(result.data?.jobProgress),
                            passedStep = passed.last(),
                            nextStep = last,
                            nextStepButtonText = result.data?.jobProgress?.get(last)?.jobStatusMsg.orEmpty()
                        )
                    }
                    is Error -> state.copy(jobProgress = HttpRequestState.ErrorState(result.message))
                }
            }
        }
    }


}