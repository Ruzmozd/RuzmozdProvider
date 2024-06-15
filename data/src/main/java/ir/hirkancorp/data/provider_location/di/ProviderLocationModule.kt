package ir.hirkancorp.data.provider_location.di

import ir.hirkancorp.data.provider_location.remote.ProviderLocationRemote
import ir.hirkancorp.data.provider_location.repository.ProviderLocationRepositoryImpl
import ir.hirkancorp.domain.provider_location.repository.ProviderLocationRepository
import org.koin.dsl.module

val providerLocationModule = module {
    single<ProviderLocationRemote> { ProviderLocationRemote(get()) }
    single<ProviderLocationRepository> { ProviderLocationRepositoryImpl(get()) }
}