package ir.hirkancorp.presenter.di

import ir.hirkancorp.presenter.core.firebaseMessaging.di.presenterUtilsModule
import org.koin.dsl.module

val presenterModule = module {
    includes(
        viewModelModule,
        presenterUtilsModule
    )
}