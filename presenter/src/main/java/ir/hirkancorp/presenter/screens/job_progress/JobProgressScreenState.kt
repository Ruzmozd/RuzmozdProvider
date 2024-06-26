package ir.hirkancorp.presenter.screens.job_progress

import ir.hirkancorp.domain.job_progress.model.JobState
import ir.hirkancorp.presenter.core.state.HttpRequestState

data class JobProgressScreenState(
    val jobProgress: HttpRequestState<List<JobState>> = HttpRequestState.LoadingState(),
    val showDialog: Boolean = false,
    val dialog: JobProgressScreenDialog? = null,
    val jobId: Int = 0,
    val ratingLoading: Boolean = false,
    val ratingError: String = "",
    val passedStep: Int = 0,
    val nextStep: Int = 0,
    val nextStepButtonText: String = "",
    val goToNextStep: Boolean = false
)


sealed class JobProgressScreenDialog {
    data class JobCompleted(val message: String) : JobProgressScreenDialog()
    data class JobCanceled(val message: String) : JobProgressScreenDialog()
}
