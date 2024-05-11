package ir.hirkancorp.data.preferences.di

import android.content.Context
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import ir.hirkancorp.data.preferences.repository.LocalServiceRepository
import ir.hirkancorp.data.preferences.repository.LocalServiceRepositoryImpl
import org.koin.dsl.module

private const val APP_PREFERENCES = "ruzmozd_provider_preferences"

val preferencesModule = module {
    single { PreferenceDataStoreFactory.create { createPreferencesDataStoreFile(get()) } }
    single<LocalServiceRepository> { LocalServiceRepositoryImpl(get()) }
}

private fun createPreferencesDataStoreFile(context: Context) = context.preferencesDataStoreFile(
    APP_PREFERENCES
)
