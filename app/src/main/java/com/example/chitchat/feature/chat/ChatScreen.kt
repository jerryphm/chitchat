package com.example.chitchat.feature.chat

import android.view.RoundedCorner
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.chitchat.model.Message
import com.example.chitchat.ui.theme.DisabledPrimary
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun ChatScreen(
    channelId: String,
) {
    val vm = hiltViewModel<ChatViewModel>()
    LaunchedEffect(Unit) {
        vm.listenForMessages(channelId)
    }

    val messages by vm.messages.collectAsState()

    ChatMessages(
        messages,
        onSend = { vm.sendMessage(channelId, it) }
    )
}

@Composable
fun ChatMessages(
    messages: List<Message>,
    onSend: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val msg = remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        modifier = modifier
            .fillMaxSize()
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .padding(it)
                .padding(12.dp)
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(messages) {
                    ChatMessage(it)
                }
            }

            Row(
                horizontalArrangement = Arrangement.Start, // Align items to the start (left) of the Row
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .background(Color.LightGray)
                    .clip(RoundedCornerShape(4.dp))
            ) {
                TextField(
                    value = msg.value,
                    onValueChange = { msg.value = it },
                    placeholder = { Text("Type something") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Send
                    ),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    keyboardActions = KeyboardActions(
                        onSend = {
                            keyboardController?.hide()
                            onSend(msg.value)
                        }
                    ),
                    modifier = Modifier
                        .weight(1f) // This will make TextField take all available space
                )
                Box(
                    modifier = Modifier
                        .clickable {
                            keyboardController?.hide()
                            onSend(msg.value)
                        }
                        .size(56.dp)  // Force the clickable area to be larger
                        .align(Alignment.CenterVertically) // Align icon properly
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = "send",
                        modifier = Modifier.align(Alignment.Center) // Keep icon centered inside the Box
                    )
                }
            }
        }
    }
}

@Composable
fun ChatMessage(
    message: Message,
    modifier: Modifier = Modifier
) {
    val isCurrentUser = message.senderId == Firebase.auth.currentUser?.uid

    Row(modifier = modifier.fillMaxWidth()) {
        if (isCurrentUser) Spacer(Modifier.width(32.dp).height(10.dp).weight(0.2f))
        Box(
            modifier = Modifier
                .weight(0.7f)
        ) {
            Text(
                text = message.message,
                color = Color.White,
                modifier = Modifier
                    .align(if (isCurrentUser) Alignment.CenterEnd else Alignment.CenterStart)
                    .background(color = if (isCurrentUser) DisabledPrimary else Color.Gray, shape = RoundedCornerShape(4.dp))
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            )
        }
        if (!isCurrentUser) Spacer(Modifier.width(32.dp).height(10.dp).weight(0.2f))
    }
}

@Preview
@Composable
fun ChatScreenPreview() {
    ChatScreen("")
}