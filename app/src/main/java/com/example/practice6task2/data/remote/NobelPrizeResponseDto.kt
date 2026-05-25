package com.example.practice6task2.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NobelPrizeResponseDto(
    val nobelPrizes: List<NobelPrizeDto> = emptyList()
)

@Serializable
data class NobelPrizeDto(
    val awardYear: String = "",
    val category: TranslatedTextDto? = null,
    val laureates: List<LaureateDto> = emptyList()
)

@Serializable
data class LaureateDto(
    val id: String = "",
    val knownName: TranslatedTextDto? = null,
    val fullName: TranslatedTextDto? = null,
    val motivation: TranslatedTextDto? = null,
    val birth: BirthDto? = null,

    @SerialName("wikipedia")
    val wikipedia: WikipediaDto? = null
)

@Serializable
data class TranslatedTextDto(
    val en: String? = null,
    val se: String? = null,
    val no: String? = null
)

@Serializable
data class BirthDto(
    val place: BirthPlaceDto? = null
)

@Serializable
data class BirthPlaceDto(
    val city: TranslatedTextDto? = null,
    val country: TranslatedTextDto? = null,
    val cityNow: TranslatedTextDto? = null,
    val countryNow: TranslatedTextDto? = null
)

@Serializable
data class WikipediaDto(
    val english: String? = null
)