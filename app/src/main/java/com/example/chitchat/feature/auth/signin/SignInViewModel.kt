package com.example.chitchat.feature.auth.signin

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

sealed interface SignInState {
    data object Idle: SignInState
    data object Loading: SignInState
    data object Success: SignInState
    data object Error: SignInState
}

@HiltViewModel
class SignInViewModel @Inject constructor() : ViewModel() {
    private val _uiState: MutableStateFlow<SignInState> = MutableStateFlow(SignInState.Idle)
    val uiState = _uiState.asStateFlow()

    var email by mutableStateOf("")
    var isEmailError by mutableStateOf(false)
    var isEmailEdited by mutableStateOf(false)
    var emailSupportingText by mutableStateOf("")

    var password by mutableStateOf("")
    var isPasswordError by mutableStateOf(false)
    var isPasswordEdited by mutableStateOf(false)
    var passwordSupportingText by mutableStateOf("")

    fun validateEmail() {
        isEmailError = !"^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$".toRegex().matches(email)
        emailSupportingText = if (email.isNotEmpty()) "Incorrect email format" else "This field is required"
    }

    fun validatePassword() {
        isPasswordError = password.length < 8
        passwordSupportingText = if (password.isEmpty()) "This field is required" else "Password must be at least 8 characters long"
    }

    fun validateTextFields() {
        validateEmail()
        validatePassword()
    }
}