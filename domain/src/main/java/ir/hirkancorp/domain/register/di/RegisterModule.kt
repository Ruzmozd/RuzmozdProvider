package ir.hirkancorp.domain.register.di

import ir.hirkancorp.domain.register.use_cases.RegisterUserUseCase
import ir.hirkancorp.domain.register.use_cases.TempSaveNationalCodeUseCase
import org.koin.dsl.module

val registerUserUseCaseModule = module {
    single { RegisterUserUseCase(get()) }
    single { TempSaveNationalCodeUseCase(get()) }
}