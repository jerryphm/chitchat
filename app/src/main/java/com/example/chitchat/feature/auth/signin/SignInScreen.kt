package com.example.chitchat.feature.auth.signin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.example.chitchat.feature.auth.component.AuthScreenWrapper
import com.example.chitchat.feature.auth.component.AuthTextField

@Composable
fun SignInScreen(
    onAlternativeMethodPress: () -> Unit,
    modifier: Modifier = Modifier
) {
    var email by rememberSaveable { mutableStateOf("") }
    var isEmailError by rememberSaveable { mutableStateOf(false) }
    var isEmailEdited by rememberSaveable { mutableStateOf(false) }
    var emailSupportingText by rememberSaveable { mutableStateOf("") }

    var password by rememberSaveable { mutableStateOf("") }
    var isPasswordError by rememberSaveable { mutableStateOf(false) }
    var isPasswordEdited by rememberSaveable { mutableStateOf(false) }
    var passwordSupportingText by rememberSaveable { mutableStateOf("") }

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

    AuthScreenWrapper(
        nextButtonTitle = "Sign In",
        alternativeMethodTitle = "New to ChitChat? Sign Up",
        onNextPress = {
            validateTextFields()
        },
        onAlternativeMethodPress = onAlternativeMethodPress
    ) {
        AuthTextField(
            value = email,
            onValueChange = {
                email = it
                isEmailEdited = true
            },
            label = "Email",
            isError = isEmailError,
            supportingText = if (isEmailError) emailSupportingText else null,
            modifier = Modifier
                .onFocusChanged {
                    if (it.isFocused) return@onFocusChanged
                    if (isEmailEdited) validateEmail()
                }
        )
        AuthTextField(
            value = password,
            onValueChange = {
                password = it
                isPasswordEdited = true
            },
            isError = isPasswordError,
            label = "ChitChat Password",
            supportingText = if (isPasswordError) passwordSupportingText else null,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .onFocusChanged {
                    if (it.isFocused) return@onFocusChanged
                    if (isPasswordEdited) validatePassword()
                }
        )
    }
}

@Preview
@Composable
fun SignInScreenPreview() {
    SignInScreen({})
}