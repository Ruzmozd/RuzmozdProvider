package ir.hirkancorp.provider

import android.app.Application
import ir.hirkancorp.data.common.di.dataModule
import ir.hirkancorp.domain.di.domainModule
import ir.hirkancorp.presenter.di.presenterModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(
                dataModule,
                domainModule,
                presenterModule
            ))
        }
    }
}