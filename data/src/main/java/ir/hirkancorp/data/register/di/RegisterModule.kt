package ir.hirkancorp.data.register.di

import ir.hirkancorp.data.register.remote.RegisterClient
import ir.hirkancorp.data.register.repository.RegisterRepositoryImpl
import ir.hirkancorp.domain.register.repository.RegisterRepository
import org.koin.dsl.module

val registerModule = module {
    single { RegisterClient(get()) }
    single<RegisterRepository> { RegisterRepositoryImpl(get(), get()) }
}