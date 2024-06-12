package ir.hirkancorp.data.work_radius.di

import ir.hirkancorp.data.work_radius.remote.WorkRadiusRemote
import ir.hirkancorp.data.work_radius.repository.WorkRadiusRepositoryImpl
import ir.hirkancorp.domain.work_radius.repository.WorkRadiusRepository
import org.koin.dsl.module

val workRadiusModule = module {
    single<WorkRadiusRemote> { WorkRadiusRemote(get()) }
    single<WorkRadiusRepository> { WorkRadiusRepositoryImpl(get()) }
}