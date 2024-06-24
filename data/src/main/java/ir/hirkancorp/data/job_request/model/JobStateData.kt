package ir.hirkancorp.data.job_request.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class JobProgressData(
    @SerialName("jobProgress")
    val jobProgress: List<JobStateData>
)

@Serializable
data class JobStateData(
    @SerialName("job_status_msg")
    val jobStatusMsg: String,
    @SerialName("status")
    val status: Boolean,
    @SerialName("time")
    val time: String
)