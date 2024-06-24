package ir.hirkancorp.data.job_progress.di

import ir.hirkancorp.data.job_progress.remote.JobProgressClient
import ir.hirkancorp.data.job_progress.repository.JobProgressRepositoryImpl
import ir.hirkancorp.domain.job_progress.repository.JobProgressRepository
import org.koin.dsl.module

val jobProgressModule = module {
    single<JobProgressRepository> { JobProgressRepositoryImpl(get()) }
    single<JobProgressClient> { JobProgressClient(get()) }
}