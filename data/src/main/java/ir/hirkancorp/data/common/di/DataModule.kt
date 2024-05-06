package ir.hirkancorp.data.common.di

import ir.hirkancorp.data.preferences.di.preferencesModule
import org.koin.dsl.module

val dataModule = module {
    includes(
        httpClientModule,
        preferencesModule,
    )
}