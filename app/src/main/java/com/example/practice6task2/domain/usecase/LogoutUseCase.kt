package com.example.practice6task2.domain.usecase

import com.example.practice6task2.domain.repository.AuthRepository

class LogoutUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke() {
        repository.logout()
    }
}