package ir.hirkancorp.domain.work_radius.di

import ir.hirkancorp.domain.work_radius.use_cases.UpdateWorkRadiusUseCase
import org.koin.dsl.module

val workRadiusModule = module {
    single { UpdateWorkRadiusUseCase(get()) }
}