package ir.hirkancorp.domain.logout.di

import ir.hirkancorp.domain.logout.use_case.LogOutUseCase
import org.koin.dsl.module

val logOutModule = module {
    single { LogOutUseCase(get()) }
}