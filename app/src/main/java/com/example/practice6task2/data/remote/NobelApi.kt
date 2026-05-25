package com.example.practice6task2.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class NobelApi(
    private val client: HttpClient
) {
    private companion object {
        const val BASE_URL = "http://192.168.0.122:8080"
    }

    suspend fun login(
        username: String,
        password: String
    ): LoginResponseDto {
        return client.post("$BASE_URL/auth/login") {
            setBody(
                LoginRequestDto(
                    username = username,
                    password = password
                )
            )
        }.body()
    }

    suspend fun getPrizes(
        token: String
    ): List<PrizeResponseDto> {
        return client.get("$BASE_URL/prizes") {
            bearerAuth(token)
        }.body()
    }

    suspend fun getFavoritePrizes(
        token: String
    ): List<FavoritePrizeResponseDto> {
        return client.get("$BASE_URL/users/me/prizes") {
            bearerAuth(token)
        }.body()
    }

    suspend fun addFavoritePrize(
        token: String,
        prizeId: Int
    ) {
        client.post("$BASE_URL/users/me/prizes/$prizeId") {
            bearerAuth(token)
        }
    }

    suspend fun deleteFavoritePrize(
        token: String,
        prizeId: Int
    ) {
        client.delete("$BASE_URL/users/me/prizes/$prizeId") {
            bearerAuth(token)
        }
    }
}