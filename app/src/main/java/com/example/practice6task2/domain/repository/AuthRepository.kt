package com.example.practice6task2.domain.repository

interface AuthRepository {
    suspend fun login(username: String, password: String)

    suspend fun logout()
}