package com.example.practice6task2.data.repository

import com.example.practice6task2.data.local.TokenDataStore
import com.example.practice6task2.data.remote.NobelApi
import com.example.practice6task2.data.remote.toDomain
import com.example.practice6task2.domain.model.NobelPrize
import com.example.practice6task2.domain.repository.NobelRepository
import kotlinx.coroutines.flow.first

class NobelRepositoryImpl(
    private val api: NobelApi,
    private val tokenDataStore: TokenDataStore
) : NobelRepository {

    override suspend fun getNobelPrizes(
        year: String?,
        category: String?
    ): List<NobelPrize> {
        val token = tokenDataStore.token.first()
            ?: throw IllegalStateException("Токен авторизации не найден")

        val prizes = api.getPrizes(token).map { prizeDto ->
            prizeDto.toDomain()
        }

        return prizes.filter { prize ->
            val yearMatches = year.isNullOrBlank() || prize.awardYear == year
            val categoryMatches = category.isNullOrBlank() || prize.category == category

            yearMatches && categoryMatches
        }
    }
}