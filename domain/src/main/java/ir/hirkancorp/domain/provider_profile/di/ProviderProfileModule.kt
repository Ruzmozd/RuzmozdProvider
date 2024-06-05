package ir.hirkancorp.domain.provider_profile.di

import ir.hirkancorp.domain.provider_profile.use_cases.ProviderProfileUseCase
import org.koin.dsl.module

val providerProfileModule = module{
    single { ProviderProfileUseCase(get()) }
}