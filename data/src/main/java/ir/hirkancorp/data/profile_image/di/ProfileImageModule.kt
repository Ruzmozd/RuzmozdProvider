package ir.hirkancorp.data.profile_image.di

import ir.hirkancorp.data.profile_image.remote.ProfileImageClient
import ir.hirkancorp.data.profile_image.repository.ProfileImageRepositoryImpl
import ir.hirkancorp.domain.profile_image.repository.ProfileImageRepository
import org.koin.dsl.module

val profileImageModule = module {
    single { ProfileImageClient(get(), get()) }
    single<ProfileImageRepository> { ProfileImageRepositoryImpl(get()) }
}