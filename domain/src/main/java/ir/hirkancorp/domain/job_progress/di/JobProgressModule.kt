package ir.hirkancorp.domain.job_progress.di

import ir.hirkancorp.domain.job_progress.use_cases.JobProgressUseCase
import org.koin.dsl.module

val jobProgressModule = module {

    single<JobProgressUseCase> { JobProgressUseCase(get()) }

}