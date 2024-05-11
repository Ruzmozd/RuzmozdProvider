package ir.hirkancorp.domain.profile_image.di

import ir.hirkancorp.domain.profile_image.use_cases.ProfileImageUseCase
import org.koin.dsl.module

val profileImageUseCaseModule = module {
    single { ProfileImageUseCase(get()) }
}