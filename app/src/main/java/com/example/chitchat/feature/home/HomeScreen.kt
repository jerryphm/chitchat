package com.example.chitchat.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.chitchat.ui.theme.DisabledPrimary
import com.example.chitchat.ui.theme.Primary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onChannelPress: (channelId: String) -> Unit,
    modifier: Modifier = Modifier) {
    val homeViewModel: HomeViewModel = hiltViewModel()
    val channels by homeViewModel.channels.collectAsState()

    val isAddingChannelModalVisible = remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Available channel") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {isAddingChannelModalVisible.value = true},
                shape = RoundedCornerShape(4.dp),
                containerColor = Primary,
                contentColor = Color.White,
            ) {
                Text(
                    text = "Add channel",
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                )
            }
        },
        modifier = modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(channels) {
                    Column(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(DisabledPrimary)
                            .clickable {
                                onChannelPress(it.id)
                            }
                            .fillMaxWidth()
                            .padding(vertical = 12.dp, horizontal = 16.dp)
                    ) {
                        Text(
                            text = it.id,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
        }
    }

    if (isAddingChannelModalVisible.value) {
        ModalBottomSheet(
            onDismissRequest = { isAddingChannelModalVisible.value = false },
            sheetState = sheetState,
        ) {
            AddChannelDialog(onAddChannel = { homeViewModel.addChannel(it) })
        }
    }
}

@Composable
fun AddChannelDialog(
    onAddChannel: (String) -> Unit,
) {
    val channelName = remember { mutableStateOf("") }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(16.dp)
    ) {
        Text(text = "Add channel")
        Spacer(Modifier.padding(8.dp))
        TextField(
            value = channelName.value,
            onValueChange = {channelName.value = it},
            label = { Text(text = "Channel name") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(Modifier.padding(8.dp))
        Button(
            onClick = {
                onAddChannel(channelName.value)
            },
            shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Primary
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Add")
        }
    }
}