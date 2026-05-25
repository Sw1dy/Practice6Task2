package com.example.practice6task2.domain.model

data class Laureate(
    val id: String,
    val fullName: String,
    val motivation: String,
    val birthCountry: String?,
    val birthPlace: String?,
    val portraitUrl: String?
)