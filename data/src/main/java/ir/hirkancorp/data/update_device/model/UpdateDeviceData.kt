package ir.hirkancorp.data.update_device.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateDeviceData(
    @SerialName("job_id")
    val jobId: Int? = null
)
