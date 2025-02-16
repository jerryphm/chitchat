package com.example.chitchat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chitchat.feature.auth.signin.SignInScreen
import com.example.chitchat.feature.auth.signup.SignUpScreen
import com.example.chitchat.feature.home.HomeScreen
import com.example.chitchat.ui.theme.ChitChatTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import dagger.hilt.android.AndroidEntryPoint

enum class ChitChatScreens {
    SIGN_IN,
    SIGN_UP,
    HOME,
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        auth = Firebase.auth
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChitChatTheme {
                App(auth)
            }
        }
    }
}


@Composable
fun App(
    auth: FirebaseAuth,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    var isSignedIn by rememberSaveable { mutableStateOf(auth.currentUser != null) }
    val destination = if (isSignedIn) {
        ChitChatScreens.HOME.name
    } else {
        ChitChatScreens.SIGN_IN.name
    }

    DisposableEffect(auth) {
        val listener = FirebaseAuth.AuthStateListener { auth ->
            isSignedIn = auth.currentUser != null
        }
        auth.addAuthStateListener(listener)
        onDispose {
            auth.removeAuthStateListener(listener)
        }
    }

    LaunchedEffect(isSignedIn) {
        navController.navigate(destination) {
            popUpTo(0)
            launchSingleTop = true
        }
    }

    Surface(
        modifier = modifier.fillMaxSize()
    ) {
        NavHost(
            navController = navController,
            startDestination = destination
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