package ir.hirkancorp.domain.login.di

import ir.hirkancorp.domain.login.use_cases.LoginUseCase
import org.koin.dsl.module

val loginUseCaseModule = module {
    single { LoginUseCase(get()) }
}