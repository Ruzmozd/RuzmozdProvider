package ir.hirkancorp.domain.provider_status.di

import ir.hirkancorp.domain.provider_status.use_cases.ProviderStatusUseCase
import org.koin.dsl.module

val providerStatusModule = module {
    single { ProviderStatusUseCase(get()) }
}