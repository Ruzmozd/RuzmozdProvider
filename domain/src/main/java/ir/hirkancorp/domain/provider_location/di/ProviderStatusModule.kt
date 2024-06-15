package ir.hirkancorp.domain.provider_status.di

import ir.hirkancorp.domain.provider_status.use_cases.ProviderLocationUseCase
import org.koin.dsl.module

val providerLocationModule = module {
    single { ProviderLocationUseCase(get()) }
}