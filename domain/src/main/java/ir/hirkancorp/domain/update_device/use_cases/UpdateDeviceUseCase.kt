package ir.hirkancorp.domain.update_device.use_cases

import ir.hirkancorp.domain.update_device.model.UpdateDevice
import ir.hirkancorp.domain.update_device.repository.UpdateDeviceRepository
import ir.hirkancorp.domain.utils.ApiResult
import ir.hirkancrop.domain.common.keys.CommonKeys
import kotlinx.coroutines.flow.Flow

class UpdateDeviceUseCase(private val updateDeviceRepository: UpdateDeviceRepository) {

    suspend operator fun invoke(deviceId: String): Flow<ApiResult<UpdateDevice>> =
        updateDeviceRepository.updateDevice(deviceType = CommonKeys.DEVICE_TYPE, deviceId = deviceId)

}