package com.nek12.beautylab.data.di

import com.nek12.beautylab.data.repo.BeautyLabRepo
import org.koin.dsl.module

val repoModule = module {
    single { BeautyLabRepo(get(), get()) }
}
