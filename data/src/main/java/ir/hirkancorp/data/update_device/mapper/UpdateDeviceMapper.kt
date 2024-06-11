package ir.hirkancorp.data.update_device.mapper

import ir.hirkancorp.domain.update_device.model.UpdateDevice
import ir.hirkancorp.data.update_device.model.UpdateDeviceData

fun UpdateDeviceData.toDomain(): UpdateDevice = UpdateDevice(
    jobId = jobId
)