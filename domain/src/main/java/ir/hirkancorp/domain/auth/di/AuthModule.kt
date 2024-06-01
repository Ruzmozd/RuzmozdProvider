package ir.hirkancorp.domain.auth.di

import ir.hirkancorp.domain.auth.use_cases.AuthUseCase
import org.koin.dsl.module

val authModule = module {
    single { AuthUseCase(get()) }
}