package com.example.practice6task2.domain.model

data class NobelPrize(
    val id: String,
    val awardYear: String,
    val category: String,
    val laureates: List<Laureate>
)