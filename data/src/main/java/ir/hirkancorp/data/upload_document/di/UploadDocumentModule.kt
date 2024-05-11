package ir.hirkancorp.data.upload_document.di

import ir.hirkancorp.data.common.static_data.TempDataRepository
import ir.hirkancorp.data.upload_document.remote.UploadDocumentDataClient
import ir.hirkancorp.data.upload_document.repository.UploadDocumentRepositoryImpl
import ir.hirkancorp.domain.upload_document.repository.UploadDocumentRepository
import org.koin.dsl.module

val uploadDocumentModule = module {
    single { UploadDocumentDataClient(get(), get()) }
    single { TempDataRepository<String>() }
    single<UploadDocumentRepository> { UploadDocumentRepositoryImpl(get(),get()) }
}