package ir.hirkancorp.domain.upload_document.di

import ir.hirkancorp.domain.upload_document.use_cases.GetTempNationalCodeUseCase
import ir.hirkancorp.domain.upload_document.use_cases.UploadDocumentUseCase
import org.koin.dsl.module

val uploadDocumentUseCaseModule = module {
    single { UploadDocumentUseCase(get()) }
    single { GetTempNationalCodeUseCase(get()) }
}