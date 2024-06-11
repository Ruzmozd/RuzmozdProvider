package ir.hirkancorp.data.update_device.repository

import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.http.HttpStatusCode.Companion.Unauthorized
import io.ktor.http.HttpStatusCode.Companion.UnprocessableEntity
import ir.hirkancorp.data.common.api_utils.commonRequest
import ir.hirkancorp.data.common.model.HttpResponseModel
import ir.hirkancorp.data.update_device.mapper.toDomain
import ir.hirkancorp.data.update_device.model.UpdateDeviceData
import ir.hirkancorp.data.update_device.remote.UpdateDeviceClient
import ir.hirkancorp.domain.utils.ApiResult
import ir.hirkancorp.domain.update_device.model.UpdateDevice
import ir.hirkancorp.domain.update_device.repository.UpdateDeviceRepository
import kotlinx.coroutines.flow.Flow

class UpdateDeviceRepositoryImpl(private val httpClient: UpdateDeviceClient) :
    UpdateDeviceRepository {
    override suspend fun updateDevice(
        deviceType: String,
        deviceId: String
    ): Flow<ApiResult<UpdateDevice>> = commonRequest(
        httpResponse = { httpClient.updateDevice(deviceType = deviceType, deviceId = deviceId) },
        errorCodes = listOf(Unauthorized, UnprocessableEntity),
        successAction = Pair(OK) { response ->
            response.body<HttpResponseModel<UpdateDeviceData>>().data.toDomain()
        })

}