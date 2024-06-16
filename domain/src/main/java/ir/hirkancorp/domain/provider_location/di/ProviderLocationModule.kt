package ir.hirkancorp.domain.provider_location.di

import ir.hirkancorp.domain.provider_location.use_cases.ProviderLocationUseCase
import org.koin.dsl.module

val providerLocationModule = module {
    single { ProviderLocationUseCase(get()) }
}