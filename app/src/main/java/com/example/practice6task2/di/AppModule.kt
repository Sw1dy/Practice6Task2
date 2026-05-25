package com.example.practice6task2.di

import com.example.practice6task2.data.remote.NobelApi
import com.example.practice6task2.data.repository.NobelRepositoryImpl
import com.example.practice6task2.domain.repository.NobelRepository
import com.example.practice6task2.domain.usecase.GetNobelPrizesUseCase
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

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
        }

    private val nobelApi: NobelApi =
        NobelApi(httpClient)

    private val nobelRepository: NobelRepository =
        NobelRepositoryImpl(nobelApi)

    val getNobelPrizesUseCase: GetNobelPrizesUseCase =
        GetNobelPrizesUseCase(nobelRepository)
}