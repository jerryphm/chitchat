package com.example.chitchat.feature.home

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.chitchat.MainActivity

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val activity = LocalActivity.current as MainActivity

    Scaffold(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = 16.dp)
        ) {
            Text("Home screen")
            Button(
                onClick = {
                    activity.auth.signOut()
                }
            ) {
                Text("Logout")
            }
        }
    }
}