package com.example.practice6task2.data.remote

import com.example.practice6task2.domain.model.Laureate
import com.example.practice6task2.domain.model.NobelPrize

fun NobelPrizeDto.toDomain(): NobelPrize {
    return NobelPrize(
        awardYear = awardYear,
        category = category?.en.orEmpty(),
        laureates = laureates.map { it.toDomain() }
    )
}

fun LaureateDto.toDomain(): Laureate {
    return Laureate(
        id = id,
        fullName = fullName?.en
            ?: knownName?.en
            ?: "Unknown laureate",
        motivation = motivation?.en ?: "No motivation provided",
        birthCountry = birth?.place?.countryNow?.en
            ?: birth?.place?.country?.en,
        birthPlace = birth?.place?.cityNow?.en
            ?: birth?.place?.city?.en,
        portraitUrl = null
    )
}