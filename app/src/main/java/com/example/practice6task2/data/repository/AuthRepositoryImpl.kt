package com.example.practice6task2.data.repository

import com.example.practice6task2.data.local.TokenDataStore
import com.example.practice6task2.data.remote.NobelApi
import com.example.practice6task2.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val api: NobelApi,
    private val tokenDataStore: TokenDataStore
) : AuthRepository {

    override suspend fun login(username: String, password: String) {
        val response = api.login(
            username = username,
            password = password
        )

        tokenDataStore.saveToken(response.token)
    }

    override suspend fun logout() {
        tokenDataStore.clearToken()
    }
}