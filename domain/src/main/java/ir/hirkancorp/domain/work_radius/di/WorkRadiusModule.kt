package ir.hirkancorp.domain.work_radius.di

import ir.hirkancorp.domain.work_radius.use_cases.UpdateWorkRadiususeCase
import org.koin.dsl.module

val workRadiusModule = module {
    single { UpdateWorkRadiususeCase(get()) }
}