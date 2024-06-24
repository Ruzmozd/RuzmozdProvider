package ir.hirkancorp.domain.job_request.di

import ir.hirkancorp.domain.job_request.use_cases.AcceptJobRequestUseCase
import org.koin.dsl.module

val jobRequestModule = module {
    single { AcceptJobRequestUseCase(get()) }
}