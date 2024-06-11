package ir.hirkancorp.domain.di

import ir.hirkancorp.domain.auth.di.authModule
import ir.hirkancorp.domain.login.di.loginUseCaseModule
import ir.hirkancorp.domain.logout.di.logOutModule
import ir.hirkancorp.domain.profile_image.di.profileImageUseCaseModule
import ir.hirkancorp.domain.provider_profile.di.providerProfileModule
import ir.hirkancorp.domain.provider_status.di.providerStatusModule
import ir.hirkancorp.domain.register.di.registerUserUseCaseModule
import ir.hirkancorp.domain.update_device.di.updateDeviceModule
import ir.hirkancorp.domain.upload_document.di.uploadDocumentUseCaseModule
import org.koin.dsl.module

val domainModule = module {
    includes(
        logOutModule,
        loginUseCaseModule,
        profileImageUseCaseModule,
        registerUserUseCaseModule,
        uploadDocumentUseCaseModule,
        authModule,
        providerProfileModule,
        providerStatusModule,
        updateDeviceModule
    )
}