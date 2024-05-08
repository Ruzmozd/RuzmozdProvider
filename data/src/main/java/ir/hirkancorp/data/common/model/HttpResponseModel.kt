package ir.hirkancorp.data.common.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HttpResponseModel<T>(
    @SerialName("success")
    var success: Boolean = false,
    @SerialName("status_code")
    var statusCode: Int = 0,
    @SerialName("status_message")
    var statusMessage: String = "",
    @SerialName("data")
    var data: T,
)