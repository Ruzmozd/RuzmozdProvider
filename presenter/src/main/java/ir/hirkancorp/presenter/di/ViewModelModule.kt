package ir.hirkancorp.presenter.di

import ir.hirkancorp.presenter.screens.login.LoginViewModel
import ir.hirkancorp.presenter.screens.profile_image.ProfileImageViewModel
import ir.hirkancorp.presenter.screens.register.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { ProfileImageViewModel(get()) }
}