package com.example.practice6task2.domain.model

data class NobelPrize(
    val awardYear: String,
    val category: String,
    val laureates: List<Laureate>
)