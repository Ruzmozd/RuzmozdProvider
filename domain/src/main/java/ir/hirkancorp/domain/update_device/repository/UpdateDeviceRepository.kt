package ir.hirkancorp.domain.update_device.repository

import ir.hirkancorp.domain.update_device.model.UpdateDevice
import ir.hirkancorp.domain.utils.ApiResult
import kotlinx.coroutines.flow.Flow

interface UpdateDeviceRepository {

    suspend fun updateDevice(deviceType: String, deviceId: String): Flow<ApiResult<UpdateDevice>>

}