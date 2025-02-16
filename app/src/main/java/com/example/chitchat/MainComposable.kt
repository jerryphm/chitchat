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
import com.example.chitchat.feature.home.HomeScreen

enum class ChitChatScreens {
    SIGN_IN,
    SIGN_UP,
    HOME,
}

@Composable
fun MainComposable(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    Surface(
        modifier = modifier.fillMaxSize()
    ) {
        NavHost(
            navController = navController,
            startDestination = ChitChatScreens.SIGN_IN.name
        ) {
            composable(ChitChatScreens.SIGN_IN.name) {
                SignInScreen(
                    onAlternativeMethodPress = { navController.navigate(ChitChatScreens.SIGN_UP.name) }
                )
            }
            composable(ChitChatScreens.SIGN_UP.name) {
                SignUpScreen(
                    onAlternativeMethodPress = { navController.popBackStack(ChitChatScreens.SIGN_IN.name, false) }
                )
            }
            composable(ChitChatScreens.HOME.name) {
                HomeScreen()
            }
        }
    }
}

@Preview
@Composable
fun MainComposablePreview() {
    MainComposable()
}
