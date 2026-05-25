package com.example.practice6task2.di

import android.content.Context
import com.example.practice6task2.data.local.TokenDataStore
import com.example.practice6task2.data.remote.NobelApi
import com.example.practice6task2.data.repository.NobelRepositoryImpl
import com.example.practice6task2.domain.repository.NobelRepository
import com.example.practice6task2.domain.usecase.GetNobelPrizesUseCase
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.contentType
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import com.example.practice6task2.data.repository.AuthRepositoryImpl
import com.example.practice6task2.domain.repository.AuthRepository
import com.example.practice6task2.domain.usecase.LoginUseCase
import com.example.practice6task2.domain.usecase.LogoutUseCase

object AppModule {

    private val httpClient: HttpClient =
        HttpClient(Android) {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        isLenient = true
                    }
                )
            }

            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.INFO
            }

            install(HttpTimeout) {
                requestTimeoutMillis = 15000
                connectTimeoutMillis = 15000
                socketTimeoutMillis = 15000
            }

            defaultRequest {
                contentType(ContentType.Application.Json)
            }
        }

    private val nobelApi: NobelApi =
        NobelApi(httpClient)

    fun provideTokenDataStore(context: Context): TokenDataStore {
        return TokenDataStore(context.applicationContext)
    }

    fun provideNobelRepository(context: Context): NobelRepository {
        return NobelRepositoryImpl(
            api = nobelApi,
            tokenDataStore = provideTokenDataStore(context)
        )
    }

    fun provideGetNobelPrizesUseCase(context: Context): GetNobelPrizesUseCase {
        return GetNobelPrizesUseCase(
            repository = provideNobelRepository(context)
        )
    }

    fun provideNobelApi(): NobelApi {
        return nobelApi
    }

    fun provideAuthRepository(context: Context): AuthRepository {
        return AuthRepositoryImpl(
            api = nobelApi,
            tokenDataStore = provideTokenDataStore(context)
        )
    }

    fun provideLoginUseCase(context: Context): LoginUseCase {
        return LoginUseCase(
            repository = provideAuthRepository(context)
        )
    }

    fun provideLogoutUseCase(context: Context): LogoutUseCase {
        return LogoutUseCase(
            repository = provideAuthRepository(context)
        )
    }
}