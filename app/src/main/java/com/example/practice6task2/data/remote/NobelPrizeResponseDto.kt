package com.example.practice6task2.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class PrizeResponseDto(
    val year: Int,
    val category: String,
    val laureates: List<LaureateResponseDto> = emptyList()
)

@Serializable
data class LaureateResponseDto(
    val id: Int,
    val fullName: String,
    val portion: String? = null,
    val motivation: String,
    val portraitUrl: String? = null
)

@Serializable
data class FavoritePrizeResponseDto(
    val id: Int,
    val year: Int,
    val category: String,
    val fullName: String,
    val motivation: String,
    val detailLink: String? = null
)