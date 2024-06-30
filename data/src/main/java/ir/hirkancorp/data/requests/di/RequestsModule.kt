package ir.hirkancorp.data.requests.di

import ir.hirkancorp.data.requests.remote.RequestsClient
import ir.hirkancorp.data.requests.repository.RequestsRepositoryImpl
import ir.hirkancorp.domain.requests.repository.RequestsRepository
import org.koin.dsl.module

val requestsModule = module {
    single<RequestsClient> { RequestsClient(get()) }
    single<RequestsRepository> { RequestsRepositoryImpl(get()) }
}