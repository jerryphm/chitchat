package com.example.chitchat.feature.auth.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chitchat.R
import com.example.chitchat.ui.theme.DisabledPrimary
import com.example.chitchat.ui.theme.Primary

@Composable
fun AuthScreenWrapper(
    onNextPress: () -> Unit,
    nextButtonTitle: String,
    onAlternativeMethodPress: () -> Unit,
    alternativeMethodTitle: String,
    content: @Composable () -> Unit,
) {
    val focusManager = LocalFocusManager.current

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9F0EC))
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                focusManager.clearFocus()
            }
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = "logo",
                modifier = Modifier
                    .size(120.dp)
                    .background(Color.White)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(Modifier.height(20.dp))
            Text(
                text = "Welcome to ChitChat!",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(Modifier.height(40.dp))

            // sign in or sign up input fields
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                content()
            }

            Spacer(Modifier.height(28.dp))
            Button(
                onClick = {
                    onNextPress()
                    focusManager.clearFocus()
                },
                shape = RoundedCornerShape(4.dp),
                colors = ButtonColors(
                    containerColor = Primary,
                    contentColor = Color.White,
                    disabledContentColor = DisabledPrimary,
                    disabledContainerColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = nextButtonTitle,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            TextButton(
                onClick = {
                    onAlternativeMethodPress()
                    focusManager.clearFocus()
                }
            ) {
                Text(
                    text = alternativeMethodTitle,
                    color = Color.Black,
                )
            }
        }
    }
}

@Preview
@Composable
fun AuthScreenPreview() {
    AuthScreenWrapper(
        onNextPress = {},
        onAlternativeMethodPress = {},
        nextButtonTitle = "",
        alternativeMethodTitle = "",
    ) {}
}