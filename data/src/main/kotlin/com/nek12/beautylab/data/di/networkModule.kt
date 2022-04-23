package com.nek12.beautylab.data.di

import com.nek12.beautylab.core.BACKEND_URL
import com.nek12.beautylab.data.net.AuthManager
import com.nek12.beautylab.data.net.api.BeautyLabApi
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import logcat.LogPriority
import logcat.logcat
import org.koin.dsl.module


val networkModule = module {
    single { provideJson() }
    single { provideHttpClient(get(), get()) }
    single { AuthManager(get()) }
    single { BeautyLabApi(get()) }
}

@OptIn(ExperimentalSerializationApi::class)
private fun provideJson() = Json {
    explicitNulls = false
    ignoreUnknownKeys = true
    encodeDefaults = true
}

private fun provideHttpClient(json: Json, authManager: AuthManager) = HttpClient(CIO) {
    expectSuccess = true

    install(Logging) {
        logger = object : Logger {
            override fun log(message: String) {
                logcat(LogPriority.VERBOSE) { message }
            }
        }
        level = LogLevel.ALL
    }
    install(ContentNegotiation) { json(json) }

    install(Auth) {
        bearer {
            loadTokens { authManager.tokens }

            refreshTokens { authManager.getTokens(client) }
        }
    }

    defaultRequest {
        url(BACKEND_URL)
    }

    install(HttpRequestRetry) {
        retryOnServerErrors(maxRetries = 1)
        constantDelay(500)
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
