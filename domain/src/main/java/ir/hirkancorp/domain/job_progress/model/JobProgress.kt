package ir.hirkancorp.domain.job_progress.model

data class JobProgress(
    val jobProgress: List<JobState> = emptyList(),
)

data class JobState(
    val jobStatusMsg: String = "",
    val status: Boolean = false,
    val time: String = ""
)