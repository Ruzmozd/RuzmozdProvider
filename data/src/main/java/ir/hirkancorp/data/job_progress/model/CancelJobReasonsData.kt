package ir.hirkancorp.data.job_progress.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CancelJobReasonsData(
    @SerialName("cancel_reasons")
    val cancelJobReasons: List<CancelReasonData>
)

@Serializable
data class CancelReasonData(
    @SerialName("cancelled_by")
    val cancelledBy: String,
    @SerialName("id")
    val id: Int,
    @SerialName("masterservice_id")
    val masterServiceId: Int,
    @SerialName("reason")
    val reason: String,
    @SerialName("status")
    val status: String
)