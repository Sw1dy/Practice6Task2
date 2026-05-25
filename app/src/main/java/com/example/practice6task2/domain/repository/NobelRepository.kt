package com.example.practice6task2.domain.repository

import com.example.practice6task2.domain.model.NobelPrize

interface NobelRepository {

    suspend fun getNobelPrizes(
        year: String? = null,
        category: String? = null
    ): List<NobelPrize>
}