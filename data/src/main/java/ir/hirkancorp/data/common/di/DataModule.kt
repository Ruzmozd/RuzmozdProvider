package ir.hirkancorp.data.common.di

import ir.hirkancorp.data.login.di.loginModule
import ir.hirkancorp.data.logout.di.logOutModule
import ir.hirkancorp.data.preferences.di.preferencesModule
import ir.hirkancorp.data.profile_image.di.profileImageModule
import ir.hirkancorp.data.register.di.registerModule
import ir.hirkancorp.data.upload_document.di.uploadDocumentModule
import org.koin.dsl.module

val dataModule = module {
    includes(
        httpClientModule,
        preferencesModule,
        loginModule,
        logOutModule,
        profileImageModule,
        registerModule,
        uploadDocumentModule
    )
}