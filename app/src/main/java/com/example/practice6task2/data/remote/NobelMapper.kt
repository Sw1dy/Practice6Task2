package com.example.practice6task2.data.remote

import com.example.practice6task2.domain.model.Laureate
import com.example.practice6task2.domain.model.NobelPrize

fun PrizeResponseDto.toDomain(): NobelPrize {
    return NobelPrize(
        id = "${year}_${category}",
        awardYear = year.toString(),
        category = category,
        laureates = laureates.map { it.toDomain() }
    )
}

fun LaureateResponseDto.toDomain(): Laureate {
    return Laureate(
        id = id.toString(),
        fullName = fullName,
        motivation = motivation,
        birthCountry = null,
        birthPlace = null,
        portraitUrl = portraitUrl,
        portion = portion
    )
}