package com.example.chitchat.feature.auth.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

sealed interface SignUpState {
    data object Idle: SignUpState
    data object Loading: SignUpState
    data object Success: SignUpState
    data object Error: SignUpState
}

@HiltViewModel
class SignUpViewModel @Inject constructor() : ViewModel() {
    private val _uiState: MutableStateFlow<SignUpState> = MutableStateFlow(SignUpState.Idle)
    val uiState = _uiState.asStateFlow()

    var displayName by  mutableStateOf("")
    var isDisplayNameError by  mutableStateOf(false)
    var isDisplayNameEdited by  mutableStateOf(false)
    var displayNameSupportingText by  mutableStateOf("")

    var email by  mutableStateOf("")
    var isEmailError by  mutableStateOf(false)
    var isEmailEdited by  mutableStateOf(false)
    var emailSupportingText by  mutableStateOf("")

    var password by  mutableStateOf("")
    var isPasswordError by  mutableStateOf(false)
    var isPasswordEdited by  mutableStateOf(false)
    var passwordSupportingText by  mutableStateOf("")

    var confirmedPassword by  mutableStateOf("")
    var isConfirmedPasswordError by  mutableStateOf(false)
    var isConfirmedPasswordEdited by  mutableStateOf(false)
    var confirmedPasswordSupportingText by  mutableStateOf("")

    fun validateDisplayName() {
        isDisplayNameError = displayName.length < 3
        displayNameSupportingText = if (displayName.isEmpty()) "This field is required" else "Display name must be at least 3 characters long"
    }

    fun validateEmail() {
        isEmailError = !email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$".toRegex())
        emailSupportingText = if (email.isEmpty()) "This field is required" else "Incorrect email format"
    }

    fun validatePassword() {
        isPasswordError = password.length < 8 || password.length > 30 || !password.matches("^[a-zA-Z0-9]+$".toRegex())
        passwordSupportingText = when {
            password.isEmpty() -> "This field is required"
            password.length > 30 -> "Password must not exceed 30 characters"
            !password.matches("^[a-zA-Z0-9]+$".toRegex()) -> "Only letters and numbers allowed"
            else -> "Password must be at least 8 characters long"
        }
    }

    fun validateConfirmedPassword() {
        isConfirmedPasswordError = confirmedPassword != password || confirmedPassword.isEmpty()
        confirmedPasswordSupportingText = if (confirmedPassword.isEmpty()) "This field is required" else "Passwords do not match"
    }

    fun validateTextFields() {
        validateDisplayName()
        validateEmail()
        validatePassword()
        validateConfirmedPassword()
    }
}