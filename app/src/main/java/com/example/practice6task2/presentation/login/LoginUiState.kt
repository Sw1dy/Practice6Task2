package com.example.practice6task2.presentation.login

sealed interface LoginUiState {
    data object Idle : LoginUiState
    data object Loading : LoginUiState
    data object Success : LoginUiState

    data class Error(
        val message: String
    ) : LoginUiState
}