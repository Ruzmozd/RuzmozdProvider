package ir.hirkancorp.data.job_request.di

import ir.hirkancorp.data.job_request.remote.JobRequestRemote
import ir.hirkancorp.data.job_request.repository.JobRequestRepositoryImpl
import ir.hirkancorp.domain.job_request.repository.JobRequestRepository
import org.koin.dsl.module

val jobRequestModule = module {
    single { JobRequestRemote(get()) }
    single<JobRequestRepository> { JobRequestRepositoryImpl(get()) }
}