package com.nek12.beautylab.data.di

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val networkModule = module {
    single { provideJson() }
    single { provideHttpClient(get()) }
}

@OptIn(ExperimentalSerializationApi::class)
private fun provideJson() = Json {
    explicitNulls = false
    ignoreUnknownKeys = true
    encodeDefaults = true
}

private fun provideHttpClient(json: Json) = HttpClient(CIO) {
    install(Logging) //TODO: Probably needs additional config for Logcat
    install(ContentNegotiation) { json(json) }

    defaultRequest {
        url("https://beautylab.herokuapp.com/api/")
    }

    install(HttpRequestRetry) {
        retryOnServerErrors(maxRetries = 2)
        exponentialDelay()
    }

    engine {
        maxConnectionsCount = 1000
        endpoint {
            maxConnectionsPerRoute = 100
            pipelineMaxSize = 20
            keepAliveTime = 5000
            connectTimeout = 5000
            connectAttempts = 5
        }
    }
}
