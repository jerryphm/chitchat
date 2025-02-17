package com.example.chitchat.feature.chat

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.chitchat.model.Message
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(): ViewModel() {
    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages = _messages.asStateFlow()
    val firebaseDatabase = Firebase.database("https://chitchat-a0de6-default-rtdb.asia-southeast1.firebasedatabase.app")
    val messagesObj = firebaseDatabase.getReference("message")

    fun listenForMessages(channelId: String) {
        messagesObj.child(channelId).orderByChild("createdAt")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    val list = mutableListOf<Message>()
                    dataSnapshot.children.forEach { data ->
                        val message = data.getValue(Message::class.java)
                        message?.let {
                            list.add(it)
                        }
                    }
                    _messages.value = list
                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    Log.w("chitchat home", "Failed to read value.", error.toException())
                }
            })
    }

    fun sendMessage(channelId: String, messageText: String) {
        val message = Message(
            firebaseDatabase.reference.push().key ?: UUID.randomUUID().toString(),
            Firebase.auth.currentUser?.uid ?: "",
            "",
            messageText,
            System.currentTimeMillis(),
            Firebase.auth.currentUser?.displayName ?: "",
            null,
        )
        messagesObj.child(channelId).push().setValue(message)
    }
}