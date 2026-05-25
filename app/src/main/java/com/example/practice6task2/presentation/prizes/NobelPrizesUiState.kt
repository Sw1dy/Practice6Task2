package com.example.practice6task2.presentation.prizes

import com.example.practice6task2.domain.model.NobelPrize

sealed interface NobelPrizesUiState {
    data object Loading : NobelPrizesUiState

    data class Success(
        val prizes: List<NobelPrize>
    ) : NobelPrizesUiState

    data class Error(
        val message: String
    ) : NobelPrizesUiState
}