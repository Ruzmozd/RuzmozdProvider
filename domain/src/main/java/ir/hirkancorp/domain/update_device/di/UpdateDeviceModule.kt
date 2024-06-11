package ir.hirkancorp.domain.update_device.di

import ir.hirkancorp.domain.update_device.use_cases.UpdateDeviceUseCase
import org.koin.dsl.module

val updateDeviceModule = module {
    single<UpdateDeviceUseCase> { UpdateDeviceUseCase(get()) }
}