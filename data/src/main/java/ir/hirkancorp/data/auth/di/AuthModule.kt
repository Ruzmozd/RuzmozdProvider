package ir.hirkancorp.data.auth.di

import ir.hirkancorp.data.auth.repository.AuthRepositoryImpl
import ir.hirkancorp.domain.auth.repository.AuthRepository
import org.koin.dsl.module

val authModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get()) }
}