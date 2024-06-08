package ir.hirkancorp.data.provider_status.di

import ir.hirkancorp.data.provider_status.remote.ProviderStatusRemote
import ir.hirkancorp.data.provider_status.repository.ProviderStatusRepositoryImpl
import ir.hirkancorp.domain.provider_status.repository.ProviderStatusRepository
import org.koin.dsl.module

val providerStatusModule = module {
    single<ProviderStatusRemote> { ProviderStatusRemote(get()) }
    single<ProviderStatusRepository> { ProviderStatusRepositoryImpl(get()) }
}