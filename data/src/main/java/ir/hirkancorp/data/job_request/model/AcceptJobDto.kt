package ir.hirkancorp.data.job_request.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class AcceptJobDto(
    @SerialName("jobProgress")
    val jobId: Int? = 0
)
