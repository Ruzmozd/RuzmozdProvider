package ir.hirkancorp.presenter.di

import org.koin.dsl.module

val presenterModule = module {
    includes(
        viewModelModule
    )
}