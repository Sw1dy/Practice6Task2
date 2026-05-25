package com.example.practice6task2.data.repository

import com.example.practice6task2.data.remote.NobelApi
import com.example.practice6task2.data.remote.toDomain
import com.example.practice6task2.domain.model.NobelPrize
import com.example.practice6task2.domain.repository.NobelRepository

class NobelRepositoryImpl(
    private val api: NobelApi
) : NobelRepository {

    override suspend fun getNobelPrizes(
        year: String?,
        category: String?
    ): List<NobelPrize> {
        return api.getNobelPrizes(
            year = year,
            category = category
        ).nobelPrizes.map { prizeDto ->
            prizeDto.toDomain()
        }
    }
}