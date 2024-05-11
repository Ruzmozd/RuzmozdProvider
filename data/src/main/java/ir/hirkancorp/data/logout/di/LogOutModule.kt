package ir.hirkancorp.data.logout.di

import ir.hirkancorp.data.logout.repository.LogOutRepositoryImpl
import ir.hirkancorp.domain.logout.repository.LogOutRepository
import org.koin.dsl.module

val logOutModule = module {
    single<LogOutRepository> { LogOutRepositoryImpl(get()) }
}