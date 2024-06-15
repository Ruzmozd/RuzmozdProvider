package ir.hirkancorp.data.provider_status.di

import ir.hirkancorp.data.provider_location.remote.ProviderLocationRemote
import ir.hirkancorp.data.provider_location.repository.ProviderLocationRepositoryImpl
import ir.hirkancorp.domain.provider_status.repository.ProviderStatusRepository
import org.koin.dsl.module

val providerStatusModule = module {
    single<ProviderLocationRemote> { ProviderLocationRemote(get()) }
    single<ProviderStatusRepository> { ProviderLocationRepositoryImpl(get()) }
}