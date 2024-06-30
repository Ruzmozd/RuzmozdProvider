package ir.hirkancorp.data.job_rating.di

import ir.hirkancorp.data.job_rating.remote.JobRatingClient
import ir.hirkancorp.data.job_rating.repository.JobRatingRepositoryImpl
import ir.hirkancorp.domain.job_rating.repository.JobRatingRepository
import org.koin.dsl.module

val jobRatingModule = module {
    single<JobRatingClient> { JobRatingClient(get()) }
    single<JobRatingRepository> { JobRatingRepositoryImpl(get()) }
}