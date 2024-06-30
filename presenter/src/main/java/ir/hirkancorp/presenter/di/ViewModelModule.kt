package ir.hirkancorp.presenter.di

import ir.hirkancorp.presenter.screens.job_progress.JobProgressScreenViewModel
import ir.hirkancorp.presenter.screens.login.LoginViewModel
import ir.hirkancorp.presenter.screens.main.MainViewModel
import ir.hirkancorp.presenter.screens.profile_image.ProfileImageViewModel
import ir.hirkancorp.presenter.screens.register.RegisterViewModel
import ir.hirkancorp.presenter.screens.upload_document.UploadDocumentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel { RegisterViewModel(get(), get()) }
    viewModel { UploadDocumentViewModel(get(), get()) }
    viewModel { ProfileImageViewModel(get()) }
    viewModel { MainViewModel(get(), get(), get(), get(), get(), get(), get(), get()) }
    viewModel { JobProgressScreenViewModel(get(), get(), get(), get(), get(), get(), get()) }
}