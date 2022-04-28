package com.nek12.beautylab.di

import com.nek12.beautylab.ui.screens.cart.CartViewModel
import com.nek12.beautylab.ui.screens.home.HomeViewModel
import com.nek12.beautylab.ui.screens.login.LoginViewModel
import com.nek12.beautylab.ui.screens.news.NewsViewModel
import com.nek12.beautylab.ui.screens.orders.OrdersViewModel
import com.nek12.beautylab.ui.screens.product.details.ProductDetailsViewModel
import com.nek12.beautylab.ui.screens.product.filters.ProductFiltersViewModel
import com.nek12.beautylab.ui.screens.product.list.ProductListViewModel
import com.nek12.beautylab.ui.screens.profile.ProfileViewModel
import com.nek12.beautylab.ui.screens.signup.SignUpViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule = module {
    viewModel { LoginViewModel(get(), get()) }
    viewModel { SignUpViewModel(get()) }
    viewModel { HomeViewModel(get(), get()) }
    viewModel { CartViewModel() }
    viewModel { NewsViewModel(get()) }
    viewModel { OrdersViewModel() }
    viewModel { params -> ProductListViewModel(params.getOrNull(), get()) }
    viewModel { ProductDetailsViewModel() }
    viewModel { ProfileViewModel() }
    viewModel { params -> ProductFiltersViewModel(params.getOrNull(), get()) }
}
