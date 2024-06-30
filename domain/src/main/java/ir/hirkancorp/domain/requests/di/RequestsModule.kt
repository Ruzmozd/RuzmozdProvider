package ir.hirkancorp.domain.requests.di

import ir.hirkancorp.domain.requests.use_case.RequestsUseCase
import org.koin.dsl.module

val requestsModule = module {
    single { RequestsUseCase(get()) }
}