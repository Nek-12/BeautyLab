package com.nek12.beautylab.di

import com.nek12.beautylab.ui.screens.home.HomeViewModel
import com.nek12.beautylab.ui.screens.login.LoginViewModel
import com.nek12.beautylab.ui.screens.signup.SignUpViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule = module {
    viewModel { LoginViewModel(get(), get()) }
    viewModel { SignUpViewModel(get()) }
    viewModel { HomeViewModel(get(), get()) }
}
