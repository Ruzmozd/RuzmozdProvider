package ir.hirkancorp.data.common.di

import io.ktor.client.HttpClient
import ir.hirkancorp.data.common.client.httpClient
import org.koin.dsl.module


val httpClientModule = module {
    single<HttpClient> { httpClient(get()) }
}