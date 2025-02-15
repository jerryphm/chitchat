package com.example.chitchat.feature.auth.signup

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.example.chitchat.feature.auth.component.AuthScreenWrapper
import com.example.chitchat.feature.auth.component.AuthTextField

@Composable
fun SignUpScreen(
    onAlternativeMethodPress: () -> Unit,
    modifier: Modifier = Modifier
) {
    var displayName by rememberSaveable { mutableStateOf("") }
    var isDisplayNameError by rememberSaveable { mutableStateOf(false) }
    var isDisplayNameEdited by rememberSaveable { mutableStateOf(false) }
    var displayNameSupportingText by rememberSaveable { mutableStateOf("") }

    var email by rememberSaveable { mutableStateOf("") }
    var isEmailError by rememberSaveable { mutableStateOf(false) }
    var isEmailEdited by rememberSaveable { mutableStateOf(false) }
    var emailSupportingText by rememberSaveable { mutableStateOf("") }

    var password by rememberSaveable { mutableStateOf("") }
    var isPasswordError by rememberSaveable { mutableStateOf(false) }
    var isPasswordEdited by rememberSaveable { mutableStateOf(false) }
    var passwordSupportingText by rememberSaveable { mutableStateOf("") }

    var confirmedPassword by rememberSaveable { mutableStateOf("") }
    var isConfirmedPasswordError by rememberSaveable { mutableStateOf(false) }
    var isConfirmedPasswordEdited by rememberSaveable { mutableStateOf(false) }
    var confirmedPasswordSupportingText by rememberSaveable { mutableStateOf("") }

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

    AuthScreenWrapper(
        nextButtonTitle = "Sign Up",
        alternativeMethodTitle = "Already have an account? Sign In",
        onNextPress = {
            validateTextFields()
        },
        onAlternativeMethodPress = onAlternativeMethodPress
    ) {
        // Display Name
        AuthTextField(
            value = displayName,
            onValueChange = {
                displayName = it
                isDisplayNameEdited = true
            },
            isError = isDisplayNameError,
            supportingText = if (isDisplayNameError) displayNameSupportingText else null,
            label = "Display Name",
            modifier = Modifier.onFocusChanged {
                if (it.isFocused) return@onFocusChanged
                if (isDisplayNameEdited) validateDisplayName()
            }
        )

        // Email
        AuthTextField(
            value = email,
            onValueChange = {
                email = it
                isEmailEdited = true
            },
            isError = isEmailError,
            supportingText = if (isEmailError) emailSupportingText else null,
            label = "Email",
            modifier = Modifier.onFocusChanged {
                if (it.isFocused) return@onFocusChanged
                if (isEmailEdited) validateEmail()
            }
        )

        // Password
        AuthTextField(
            value = password,
            onValueChange = {
                password = it
                isPasswordEdited = true
            },
            isError = isPasswordError,
            supportingText = if (isPasswordError) passwordSupportingText else null,
            label = "ChitChat Password",
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.onFocusChanged {
                if (it.isFocused) return@onFocusChanged
                if (isPasswordEdited) validatePassword()
            }
        )

        // Confirm Password
        AuthTextField(
            value = confirmedPassword,
            onValueChange = {
                confirmedPassword = it
                isConfirmedPasswordEdited = true
            },
            isError = isConfirmedPasswordError,
            supportingText = if (isConfirmedPasswordError) confirmedPasswordSupportingText else null,
            label = "Confirmed ChitChat Password",
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.onFocusChanged {
                if (it.isFocused) return@onFocusChanged
                if (isConfirmedPasswordEdited) validateConfirmedPassword()
            }
        )
    }
}

@Preview
@Composable
fun SignUpScreenPreview() {
    SignUpScreen({})
}
