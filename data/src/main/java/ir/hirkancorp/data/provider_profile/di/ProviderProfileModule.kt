package ir.hirkancorp.data.provider_profile.di

import ir.hirkancorp.data.provider_profile.remote.ProviderProfileRemote
import ir.hirkancorp.data.provider_profile.repository.ProviderProfileRepositoryImpl
import ir.hirkancorp.domain.provider_profile.repository.ProviderProfileRepository
import org.koin.dsl.module


val providerProfileModule = module{
    single<ProviderProfileRemote> { ProviderProfileRemote(get(), get()) }
    single<ProviderProfileRepository> { ProviderProfileRepositoryImpl(get()) }
}