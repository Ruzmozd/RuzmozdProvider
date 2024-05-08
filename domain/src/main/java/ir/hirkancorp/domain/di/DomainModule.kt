package ir.hirkancorp.domain.di

import ir.hirkancorp.domain.login.di.loginUseCaseModule
import ir.hirkancorp.domain.logout.di.logOutModule
import ir.hirkancorp.domain.profile_image.di.profileImageUseCaseModule
import ir.hirkancorp.domain.register.di.registerUserUseCaseModule
import org.koin.dsl.module

val domainModule = module {
    includes(
        logOutModule,
        loginUseCaseModule,
        profileImageUseCaseModule,
        registerUserUseCaseModule,
    )
}