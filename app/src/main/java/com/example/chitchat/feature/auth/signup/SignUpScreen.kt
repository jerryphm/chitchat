package com.example.chitchat.feature.auth.signup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.example.chitchat.feature.auth.component.AuthScreenWrapper
import com.example.chitchat.feature.auth.component.AuthTextField

@Composable
fun SignUpScreen(modifier: Modifier = Modifier) {
    var username by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmedPassoword by rememberSaveable { mutableStateOf("") }

    AuthScreenWrapper(
        nextButtonTitle = "Sign Up",
        alternativeMethodTitle = "Already have an account? Sign In",
        onNextPress = {},
        onAlternativeMethodPress = {}
    ) {
        AuthTextField(
            value = username,
            onValueChange = { username = it },
            label = "Username",
        )
        AuthTextField(
            value = email,
            onValueChange = { email = it },
            label = "Email",
        )
        AuthTextField(
            value = password,
            onValueChange = { password = it },
            label = "ChitChat Password",
            visualTransformation = PasswordVisualTransformation()
        )
        AuthTextField(
            value = confirmedPassoword,
            onValueChange = { confirmedPassoword = it },
            label = "Confirm ChitChat Password",
            visualTransformation = PasswordVisualTransformation()
        )
    }
}

@Preview
@Composable
fun SignUpScreenPreview() {
    SignUpScreen()
}