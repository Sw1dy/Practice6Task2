package com.example.practice6task2.presentation.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.practice6task2.di.AppModule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val loginUseCase = AppModule.provideLoginUseCase(application)

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val _username = MutableStateFlow("admin")
    val username: StateFlow<String> = _username.asStateFlow()

    private val _password = MutableStateFlow("123456")
    val password: StateFlow<String> = _password.asStateFlow()

    fun onUsernameChanged(value: String) {
        _username.value = value
    }

    fun onPasswordChanged(value: String) {
        _password.value = value
    }

    fun login() {
        val usernameValue = _username.value.trim()
        val passwordValue = _password.value.trim()

        if (usernameValue.isBlank() || passwordValue.isBlank()) {
            _uiState.value = LoginUiState.Error("Введите логин и пароль")
            return
        }

        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading

            try {
                loginUseCase(
                    username = usernameValue,
                    password = passwordValue
                )

                _uiState.value = LoginUiState.Success
            } catch (e: Exception) {
                _uiState.value = LoginUiState.Error(
                    message = e.message ?: "Ошибка авторизации"
                )
            }
        }
    }

    fun resetState() {
        _uiState.value = LoginUiState.Idle
    }
}