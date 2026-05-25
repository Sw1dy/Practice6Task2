package com.example.practice6task2.presentation.prizes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practice6task2.di.AppModule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NobelPrizesViewModel : ViewModel() {

    private val getNobelPrizesUseCase = AppModule.getNobelPrizesUseCase

    private val _uiState = MutableStateFlow<NobelPrizesUiState>(NobelPrizesUiState.Loading)
    val uiState: StateFlow<NobelPrizesUiState> = _uiState.asStateFlow()

    private val _year = MutableStateFlow("")
    val year: StateFlow<String> = _year.asStateFlow()

    private val _category = MutableStateFlow("")
    val category: StateFlow<String> = _category.asStateFlow()

    init {
        loadPrizes()
    }

    fun onYearChanged(value: String) {
        _year.value = value.filter { it.isDigit() }.take(4)
    }

    fun onCategoryChanged(value: String) {
        _category.value = value
    }

    fun applyFilters() {
        loadPrizes()
    }

    fun loadPrizes() {
        viewModelScope.launch {
            _uiState.value = NobelPrizesUiState.Loading

            try {
                val prizes = getNobelPrizesUseCase(
                    year = _year.value.ifBlank { null },
                    category = _category.value.ifBlank { null }
                )

                _uiState.value = NobelPrizesUiState.Success(prizes)
            } catch (e: Exception) {
                _uiState.value = NobelPrizesUiState.Error(
                    message = e.message ?: "Не удалось загрузить данные"
                )
            }
        }
    }
}