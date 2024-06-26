package ir.hirkancorp.domain.job_progress.di

import ir.hirkancorp.domain.job_progress.use_cases.ArriveNowUseCase
import ir.hirkancorp.domain.job_progress.use_cases.BeginJobUseCase
import ir.hirkancorp.domain.job_progress.use_cases.EndJobUseCase
import ir.hirkancorp.domain.job_progress.use_cases.JobProgressUseCase
import org.koin.dsl.module

val jobProgressModule = module {

    single<JobProgressUseCase> { JobProgressUseCase(get()) }
    single<ArriveNowUseCase> { ArriveNowUseCase(get()) }
    single<BeginJobUseCase> { BeginJobUseCase(get()) }
    single<EndJobUseCase> { EndJobUseCase(get()) }

}