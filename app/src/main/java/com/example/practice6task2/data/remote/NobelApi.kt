package com.example.practice6task2.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class NobelApi(
    private val client: HttpClient
) {
    suspend fun getNobelPrizes(
        year: String?,
        category: String?
    ): NobelPrizeResponseDto {
        return client.get("https://api.nobelprize.org/2.1/nobelPrizes") {
            parameter("limit", 25)
            parameter("offset", 0)

            if (!year.isNullOrBlank()) {
                parameter("nobelPrizeYear", year)
            }

            if (!category.isNullOrBlank()) {
                parameter("nobelPrizeCategory", category)
            }
        }.body()
    }
}