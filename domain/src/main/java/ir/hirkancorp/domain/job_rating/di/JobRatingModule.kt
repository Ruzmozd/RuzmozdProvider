package ir.hirkancorp.domain.job_rating.di

import ir.hirkancorp.domain.job_rating.use_cases.JobRatingUseCase
import org.koin.dsl.module

val jobRatingModule = module {
    single<JobRatingUseCase> { JobRatingUseCase(get()) }
}