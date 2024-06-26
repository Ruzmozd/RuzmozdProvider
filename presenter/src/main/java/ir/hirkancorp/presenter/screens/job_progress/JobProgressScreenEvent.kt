package ir.hirkancorp.presenter.screens.job_progress

sealed class JobProgressScreenEvent {

    data class GetJobProgress(val jobId: Int): JobProgressScreenEvent()
    data class JobCompleted(val message: String): JobProgressScreenEvent()
    data object HideDialog: JobProgressScreenEvent()
    data class Rate(val rate: Int, val comment: String): JobProgressScreenEvent()
    data object NextStep : JobProgressScreenEvent()

}