package com.example.practice6task2.domain.usecase

import com.example.practice6task2.domain.model.NobelPrize
import com.example.practice6task2.domain.repository.NobelRepository

class GetNobelPrizesUseCase(
    private val repository: NobelRepository
) {
    suspend operator fun invoke(
        year: String? = null,
        category: String? = null
    ): List<NobelPrize> {
        return repository.getNobelPrizes(
            year = year,
            category = category
        )
    }
}