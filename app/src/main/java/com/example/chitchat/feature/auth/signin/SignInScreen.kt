package com.example.chitchat.feature.auth.signin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.chitchat.feature.auth.component.AuthScreenWrapper
import com.example.chitchat.feature.auth.component.AuthTextField


@Composable
fun SignInScreen(
    onAlternativeMethodPress: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SignInViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val email by viewModel::email
    val isEmailError by viewModel::isEmailError
    val isEmailEdited by viewModel::isEmailEdited
    val emailSupportingText by viewModel::emailSupportingText

    val password by viewModel::password
    val isPasswordError by viewModel::isPasswordError
    val isPasswordEdited by viewModel::isPasswordEdited
    val passwordSupportingText by viewModel::passwordSupportingText

    AuthScreenWrapper(
        nextButtonTitle = "Sign In",
        alternativeMethodTitle = "New to ChitChat? Sign Up",
        onNextPress = {
            viewModel.validateTextFields()
        },
        onAlternativeMethodPress = onAlternativeMethodPress
    ) {
        AuthTextField(
            value = email,
            onValueChange = {
                viewModel.email = it
                viewModel.isEmailEdited = true
            },
            label = "Email",
            isError = isEmailError,
            supportingText = if (isEmailError) emailSupportingText else null,
            modifier = Modifier
                .onFocusChanged {
                    if (it.isFocused) return@onFocusChanged
                    if (isEmailEdited) viewModel.validateEmail()
                }
        )
        AuthTextField(
            value = password,
            onValueChange = {
                viewModel.password = it
                viewModel.isPasswordEdited = true
            },
            isError = isPasswordError,
            label = "ChitChat Password",
            supportingText = if (isPasswordError) passwordSupportingText else null,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .onFocusChanged {
                    if (it.isFocused) return@onFocusChanged
                    if (isPasswordEdited) viewModel.validatePassword()
                }
        )
    }
}

@Preview
@Composable
fun SignInScreenPreview() {
    SignInScreen({})
}