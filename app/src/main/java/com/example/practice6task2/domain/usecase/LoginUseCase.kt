package com.example.practice6task2.domain.usecase

import com.example.practice6task2.domain.repository.AuthRepository

class LoginUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        username: String,
        password: String
    ) {
        repository.login(username, password)
    }
}