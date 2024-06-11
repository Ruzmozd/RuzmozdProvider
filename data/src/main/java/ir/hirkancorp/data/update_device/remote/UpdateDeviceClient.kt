package ir.hirkancorp.data.update_device.remote

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url

class UpdateDeviceClient(
    private val httpClient: HttpClient
) {

    suspend fun updateDevice(
        deviceType: String,
        deviceId: String
    ) = httpClient.get {
        url("update_device")
        parameter("device_type", deviceType)
        parameter("device_id", deviceId)
    }

}