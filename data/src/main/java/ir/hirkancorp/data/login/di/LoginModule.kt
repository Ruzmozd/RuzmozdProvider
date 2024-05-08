package ir.hirkancorp.data.login.di

import ir.hirkancorp.data.login.remote.LoginClient
import ir.hirkancorp.data.login.repository.LoginRepositoryImpl
import ir.hirkancorp.domain.login.repository.LoginRepository
import org.koin.dsl.module

val loginModule = module {
    single<LoginClient> { LoginClient(get()) }
    single<LoginRepository> { LoginRepositoryImpl(get(), get()) }
}