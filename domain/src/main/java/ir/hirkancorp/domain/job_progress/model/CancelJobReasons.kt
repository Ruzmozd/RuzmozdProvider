package ir.hirkancorp.domain.job_progress.model

data class CancelJobReasons(
    val cancelJobReasons: List<CancelReason> = emptyList()
)

data class CancelReason(
    val cancelledBy: String,
    val id: Int,
    val masterServiceId: Int,
    val reason: String,
    val status: String
)