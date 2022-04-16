package com.nek12.beautylab.data.di

import org.koin.dsl.module

val dataModule = module {
    single<CoroutineDispatchers> { RuntimeCoroutineDispatchers }
}
