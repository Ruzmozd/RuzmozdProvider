package ir.hirkancorp.data.update_device.di

import ir.hirkancorp.data.update_device.remote.UpdateDeviceClient
import ir.hirkancorp.data.update_device.repository.UpdateDeviceRepositoryImpl
import ir.hirkancorp.domain.update_device.repository.UpdateDeviceRepository
import org.koin.dsl.module

val updateDeviceModule = module {
    single { UpdateDeviceClient(get()) }
    single<UpdateDeviceRepository> { UpdateDeviceRepositoryImpl(get()) }
}