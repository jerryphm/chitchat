package com.example.chitchat

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chitchat.feature.auth.signin.SignInScreen
import com.example.chitchat.feature.auth.signup.SignUpScreen

@Composable
fun MainComposable(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    Surface(
        modifier = modifier.fillMaxSize()
    ) {
        NavHost(
            navController = navController,
            startDestination = "signIn"
        ) {
            composable("signIn") {
                SignInScreen(
                    onAlternativeMethodPress = { navController.navigate("signUp") }
                )
            }
            composable("signUp") {
                SignUpScreen(
                    onAlternativeMethodPress = { navController.navigate("signIn") }
                )
            }
        }
    }
}

@Preview
@Composable
fun MainComposablePreview() {
    MainComposable()
}